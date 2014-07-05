package edu.asu.ying.kad.messages;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class StoreMessage extends KadMessage {

  public static final MessageType TYPE = MessageType.Store;

  private final Key key;
  private final byte[] value;
  private final int hashCode;

  StoreMessage(int id, Key senderKey, int port, Key storeKey, byte[] value) {
    super(TYPE, id, senderKey, port);

    key = storeKey;
    this.value = Arrays.copyOf(value, value.length);

    int hash = super.hashCode();
    hash = 31 * hash + key.hashCode();
    hash = 31 * hash + Arrays.hashCode(value);
    hashCode = hash;
  }

  public Key storeKey() {
    return key;
  }

  public int valueLength() {
    return value.length;
  }

  public byte[] valueToByteArray() {
    return Arrays.copyOf(value, value.length);
  }

  public boolean valueEquals(byte[] bytes) {
    return Arrays.equals(value, bytes);
  }

  public void writeValueTo(OutputStream stream) throws IOException {
    stream.write(value);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    StoreMessage that = (StoreMessage) o;
    return key.equals(that.key) && that.valueEquals(value);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
