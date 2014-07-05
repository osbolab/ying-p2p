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
public final class FoundValueMessage extends KadMessage {

  public static final MessageType TYPE = MessageType.FoundValue;

  private final Key key;
  private final byte[] value;
  private final int hashCode;

  FoundValueMessage(int id, Key senderKey, int port, Key foundKey, byte[] value) {
    super(TYPE, id, senderKey, port);

    this.key = foundKey;
    this.value = Arrays.copyOf(value, value.length);

    int hash = super.hashCode();
    hash = 31 * hash + key.hashCode();
    hash = 31 * hash + Arrays.hashCode(value);
    hashCode = hash;
  }

  public Key foundKey() {
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
    FoundValueMessage that = (FoundValueMessage) o;
    return key.equals(that.key) && Arrays.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
