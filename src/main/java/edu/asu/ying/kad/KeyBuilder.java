package edu.asu.ying.kad;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public final class KeyBuilder {

  private static final class RandomHolder {
    private static final Random RANDOM = new Random();
  }

  /**
   * Gets a new empty byte array of the correct size for one key's data, minus any length
   * information.
   */
  public static byte[] newByteArray() {
    return new byte[KeyImpl.KEY_LENGTH_BYTES];
  }

  public static Key from(byte[] bytes) {
    if (bytes.length != KeyImpl.KEY_LENGTH_BYTES) {
      return null;
    }
    return new KeyImpl(Arrays.copyOf(bytes, KeyImpl.KEY_LENGTH_BYTES));
  }

  public static Key from(InputStream stream) throws IOException {
    byte[] bytes = new byte[KeyImpl.KEY_LENGTH_BYTES];
    if (stream.read(bytes) != KeyImpl.KEY_LENGTH_BYTES) {
      throw new IOException("End of stream reached while reading key.");
    }
    return new KeyImpl(bytes);
  }

  public static Key random() {
    Random random = RandomHolder.RANDOM;
    byte[] bytes = newByteArray();
    random.nextBytes(bytes);
    return new KeyImpl(bytes);
  }

  private KeyBuilder() {
  }
}
