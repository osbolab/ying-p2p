package edu.asu.ying.kad.messages;

import java.util.Arrays;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.FoundValue;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FoundValueMessage extends KadMessage {
  private final Key key;
  private final byte[] value;

  FoundValueMessage(int id, Key senderKey, int port, Key foundKey, byte[] value) {
    super(FoundValue, id, senderKey, port);

    this.key = foundKey;
    this.value = Arrays.copyOf(value, value.length);
  }

  public Key key() {
    return key;
  }

  public byte[] value() {
    return value;
  }
}
