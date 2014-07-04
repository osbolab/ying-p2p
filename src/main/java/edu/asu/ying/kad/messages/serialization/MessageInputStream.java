package edu.asu.ying.kad.messages.serialization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;
import edu.asu.ying.kad.KeyBuilder;
import edu.asu.ying.kad.messages.AckMessage;
import edu.asu.ying.kad.messages.FindNodesMessage;
import edu.asu.ying.kad.messages.FindValueMessage;
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
      case FindValue:
        message = readFindValue();
        break;
      case Store:
        message = readStore();
        break;
    }
    //noinspection unchecked
    return (T) message;
  }

  private SynMessage readSyn() throws IOException {
    KadMessage base = readBase();
    return Messages.syn(base.id(), base.senderKey(), base.senderPort());
  }

  private AckMessage readAck() throws IOException {
    return null;
  }

  private FindNodesMessage readFindNodes() throws IOException {
    return null;
  }

  private FindValueMessage readFindValue() throws IOException {
    return null;
  }

  private StoreMessage readStore() throws IOException {
    return null;
  }

  private KadMessage readBase() throws IOException {
    int id = stream.readInt();
    Key key = KeyBuilder.from(stream);
    int port = stream.readInt();
    return new DummyKadMessage(id, key, port);
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
