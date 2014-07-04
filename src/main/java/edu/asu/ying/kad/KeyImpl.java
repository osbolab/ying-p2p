package edu.asu.ying.kad;

import com.google.common.primitives.UnsignedBytes;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
final class KeyImpl implements Key {
  // SHA-1 is 160 bits or 20 bytes.
  static final int KEY_LENGTH_BYTES = 20;

  private static final KeyImpl ZERO = new KeyImpl(KeyBuilder.newByteArray());

  private final byte[] bytes;
  private final int hashCode;
  private final String asString;

  KeyImpl(byte[] bytes) {
    this.bytes = bytes;
    hashCode = Arrays.hashCode(bytes);
    asString = Hex.encodeHexString(bytes);
  }

  @Override
  public byte[] toByteArray() {
    return Arrays.copyOf(bytes, KEY_LENGTH_BYTES);
  }

  @Override
  public Key xor(Key k) {
    if (k == this) {
      return ZERO;
    }
    if (k == ZERO) {
      return this;
    }
    return k.xor(bytes);
  }

  @Override
  public Key xor(byte[] other) {
    if (other.length != KEY_LENGTH_BYTES) {
      return null;
    }
    byte[] xored = KeyBuilder.newByteArray();
    for (int i = 0; i < KEY_LENGTH_BYTES; ++i) {
      xored[i] = (byte) (bytes[i] ^ other[i]);
    }
    return new KeyImpl(xored);
  }

  @Override
  public int compareTo(Key k) {
    if (k == this) {
      return 0;
    }
    if (k == ZERO) {
      return 1;
    }
    // Don't miss the negation!
    return -k.compareTo(bytes);
  }

  @Override
  public int compareTo(byte[] other) {
    return UnsignedBytes.lexicographicalComparator().compare(bytes, other);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) { return true; }
    if (o == null) {
      return false;
    }
    if (Key.class.isAssignableFrom(o.getClass())) {
      return o.equals(bytes);
    }
    if (o instanceof byte[]) {
      return Arrays.equals(bytes, (byte[]) o);

    } else if (o instanceof Byte[]) {
      Byte[] other = (Byte[]) o;
      if (bytes.length != other.length) {
        return false;
      }
      for (int i = 0; i < bytes.length; ++i) {
        if (bytes[i] != other[i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public String toString() {
    return asString;
  }
}
