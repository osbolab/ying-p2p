package edu.asu.ying.kad.messages.serialization;


import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Endpoint;
import edu.asu.ying.kad.IpEndpoint;
import edu.asu.ying.kad.Key;
import edu.asu.ying.kad.KeyBuilder;
import edu.asu.ying.kad.RemoteNode;
import edu.asu.ying.kad.RemoteNodeProxy;
import edu.asu.ying.kad.messages.AckMessage;
import edu.asu.ying.kad.messages.FindNodesMessage;
import edu.asu.ying.kad.messages.FindValueMessage;
import edu.asu.ying.kad.messages.FoundNodesMessage;
import edu.asu.ying.kad.messages.FoundValueMessage;
import edu.asu.ying.kad.messages.KadMessage;
import edu.asu.ying.kad.messages.MessageType;
import edu.asu.ying.kad.messages.Messages;
import edu.asu.ying.kad.messages.StoreMessage;
import edu.asu.ying.kad.messages.SynMessage;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class MessageInputStream extends InputStream implements ReadsMessages {

  private final DataInputStream stream;

  public MessageInputStream(InputStream stream) {
    if (stream instanceof DataInputStream) {
      this.stream = (DataInputStream) stream;
    } else {
      this.stream = new DataInputStream(stream);
    }
  }

  @Override
  public MessageType readType() throws IOException {
    return MessageType.fromValue(read());
  }

  @Override
  public <T extends KadMessage> T read(@Nullable MessageType type) throws IOException {
    if (type == null) {
      return null;
    }
    KadMessage message = null;
    switch (type) {
      case Syn:
        message = readSyn();
        break;
      case Ack:
        message = readAck();
        break;
      case FindNodes:
        message = readFindNodes();
        break;
      case FoundNodes:
        message = readFoundNodes();
        break;
      case FindValue:
        message = readFindValue();
        break;
      case FoundValue:
        message = readFoundValue();
        break;
      case Store:
        message = readStore();
        break;
    }
    //noinspection unchecked
    return (T) message;
  }

  private KadMessage readBase() throws IOException {
    int id = stream.readInt();
    int port = stream.readInt();
    Key key = KeyBuilder.from(stream);
    return new DummyKadMessage(id, key, port);
  }

  private SynMessage readSyn() throws IOException {
    KadMessage base = readBase();
    return Messages.syn(base.id(), base.senderKey(), base.senderPort());
  }

  private AckMessage readAck() throws IOException {
    KadMessage base = readBase();
    return Messages.ack(base.id(), base.senderKey(), base.senderPort());
  }

  private FindNodesMessage readFindNodes() throws IOException {
    KadMessage base = readBase();
    Key findKey = KeyBuilder.from(stream);
    return Messages.findNodes(base.id(), base.senderKey(), base.senderPort(), findKey);
  }

  private FoundNodesMessage readFoundNodes() throws IOException {
    KadMessage base = readBase();
    int nodeCount = stream.readInt();
    Set<RemoteNode> nodes = new HashSet<>(nodeCount);
    for (int i = 0; i < nodeCount; ++i) {
      Key nodeKey = KeyBuilder.from(stream);
      int nodePort = stream.readInt();

      byte[] addrBytes = new byte[4];
      IOUtils.readFully(stream, addrBytes);
      InetAddress nodeAddr = InetAddress.getByAddress(addrBytes);

      Endpoint endpoint = new IpEndpoint(nodeAddr, nodePort);
      nodes.add(new RemoteNodeProxy(nodeKey, endpoint));
    }

    return Messages.foundNodes(base.id(), base.senderKey(), base.senderPort(), nodes);
  }

  private FindValueMessage readFindValue() throws IOException {
    KadMessage base = readBase();
    Key findKey = KeyBuilder.from(stream);
    return Messages.findValue(base.id(), base.senderKey(), base.senderPort(), findKey);
  }

  private FoundValueMessage readFoundValue() throws IOException {
    KadMessage base = readBase();
    Key foundKey = KeyBuilder.from(stream);
    byte[] value = readLengthPrefixedBytes();

    return Messages.foundValue(base.id(), base.senderKey(), base.senderPort(), foundKey, value);
  }

  private StoreMessage readStore() throws IOException {
    KadMessage base = readBase();
    Key storeKey = KeyBuilder.from(stream);
    byte[] storeValue = readLengthPrefixedBytes();
    return Messages.store(base.id(), base.senderKey(), base.senderPort(), storeKey, storeValue);
  }

  private byte[] readLengthPrefixedBytes() throws IOException {
    int len = stream.readInt();
    byte[] bytes = new byte[len];
    IOUtils.readFully(stream, bytes);
    return bytes;
  }

  @Override
  public int read() throws IOException {
    return stream.read();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return stream.read(b);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return stream.read(b, off, len);
  }

  @Override
  public long skip(long n) throws IOException {
    return stream.skip(n);
  }

  @Override
  public int available() throws IOException {
    return stream.available();
  }

  @Override
  public void close() throws IOException {
    stream.close();
  }

  @Override
  public synchronized void mark(int readlimit) {
    stream.mark(readlimit);
  }

  @Override
  public synchronized void reset() throws IOException {
    stream.reset();
  }

  @Override
  public boolean markSupported() {
    return stream.markSupported();
  }

  private static final class DummyKadMessage extends KadMessage {
    protected DummyKadMessage(int id, Key key, int port) {
      super(MessageType.Syn, id, key, port);
    }
  }
}
