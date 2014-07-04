package edu.asu.ying.kad.messages;

import java.util.Arrays;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.Store;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class StoreMessage extends KadMessage {

  private final Key key;
  private final byte[] value;

  StoreMessage(int id, Key senderKey, int port, Key storeKey, byte[] value) {
    super(Store, id, senderKey, port);

    key = storeKey;
    this.value = Arrays.copyOf(value, value.length);
  }

  public Key storeKey() {
    return key;
  }

  public byte[] storeValue() {
    return value;
  }
}
