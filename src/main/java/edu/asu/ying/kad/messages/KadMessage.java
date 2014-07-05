package edu.asu.ying.kad.messages;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public abstract class KadMessage {
  private final MessageType type;
  private final int id;
  private final Key key;
  private final int port;

  protected KadMessage(MessageType type, int id, Key key, int port) {
    this.type = type;
    this.id = id;
    this.key = key;
    this.port = port;
  }

  public MessageType type() {
    return type;
  }

  public int id() {
    return id;
  }

  public Key senderKey() {
    return key;
  }

  public int senderPort() {
    return port;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !getClass().isAssignableFrom(o.getClass())) {
      return false;
    }
    KadMessage that = (KadMessage) o;
    return type == that.type && id == that.id && port == that.port && key.equals(that.key);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + id;
    result = 31 * result + key.hashCode();
    result = 31 * result + port;
    return result;
  }
}
