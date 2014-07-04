package edu.asu.ying.kad.messages.serialization;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Endpoint;
import edu.asu.ying.kad.RemoteNode;
import edu.asu.ying.kad.messages.AckMessage;
import edu.asu.ying.kad.messages.FindNodesMessage;
import edu.asu.ying.kad.messages.FindValueMessage;
import edu.asu.ying.kad.messages.FoundNodesMessage;
import edu.asu.ying.kad.messages.KadMessage;
import edu.asu.ying.kad.messages.StoreMessage;
import edu.asu.ying.kad.messages.SynMessage;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class MessageOutputStream extends OutputStream implements WritesMessages {

  private final DataOutputStream stream;

  public MessageOutputStream(OutputStream stream) {
    if (stream instanceof DataOutputStream) {
      this.stream = (DataOutputStream) stream;
    } else {
      this.stream = new DataOutputStream(stream);
    }
  }

  @Override
  public void write(SynMessage message) throws IOException {
    write((KadMessage) message);
  }

  @Override
  public void write(AckMessage message) throws IOException {
    write((KadMessage) message);
  }

  @Override
  public void write(FindValueMessage message) throws IOException {
    write((KadMessage) message);
    stream.write(message.findKey().toByteArray());
  }

  @Override
  public void write(FindNodesMessage message) throws IOException {
    write((KadMessage) message);
    stream.write(message.findKey().toByteArray());
  }

  @Override
  public void write(FoundNodesMessage message) throws IOException {
    write((KadMessage) message);
    stream.writeInt(message.nodes().size());
    for (RemoteNode node : message.nodes()) {
      stream.write(node.key().toByteArray());
      Endpoint endpoint = node.endpoint();
      stream.writeInt(endpoint.port());
      stream.write(endpoint.inetAddress().getAddress());
    }
  }

  @Override
  public void write(StoreMessage message) throws IOException {
    write((KadMessage) message);
    stream.write(message.storeKey().toByteArray());
    stream.write(message.storeValue());
  }

  private void write(KadMessage message) throws IOException {
    stream.write(message.type().toByte());
    stream.writeInt(message.id());
    stream.writeInt(message.senderPort());
    stream.write(message.senderKey().toByteArray());
  }

  @Override
  public void write(int b) throws IOException {
    stream.write(b);
  }

  @Override
  public void write(byte[] b) throws IOException {
    stream.write(b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    stream.write(b, off, len);
  }

  @Override
  public void flush() throws IOException {
    stream.flush();
  }

  @Override
  public void close() throws IOException {
    stream.close();
  }
}
