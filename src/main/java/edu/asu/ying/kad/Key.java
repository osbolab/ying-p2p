package edu.asu.ying.kad;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface Key extends Comparable<Key> {

  /**
   * Gets a copy of the byte array representing this key.
   */
  byte[] toByteArray();

  void writeTo(OutputStream stream) throws IOException;

  /**
   * Returns a new key that is the exclusive bitwise OR of this and the given key.
   */
  Key xor(Key k);

  /**
   * Returns a new key that is the exclusive bitwise OR of this and the given bytes.
   * <p>
   * If the given byte array is not of the correct length, returns {@code null}.
   */
  Key xor(byte[] bytes);

  int compareTo(byte[] bytes);

  /**
   * @author matt@osbolab.com (Matt Barnard)
   */
  public static final class KeyDistanceComparator implements Comparator<Key> {
    private final Key key;

    public KeyDistanceComparator(Key key) {
      this.key = key;
    }

    @Override
    public int compare(Key o1, Key o2) {
      return o1.xor(key).compareTo(o2.xor(key));
    }
  }
}
