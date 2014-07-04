package edu.asu.ying.kad;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.asu.ying.kad.Key.KeyDistanceComparator;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@RunWith(JUnit4.class)
public class KeysTest {

  @Test
  public void sortByXorDistance() {
    byte[] b = KeyBuilder.newByteArray();
    Key k0 = KeyBuilder.from(b);
    b[19] = (byte) 255;
    Key k1 = KeyBuilder.from(b);
    b[18] = (byte) 255;
    Key k2 = KeyBuilder.from(b);
    b[17] = (byte) 255;
    Key k3 = KeyBuilder.from(b);
    b[16] = (byte) 255;
    Key k4 = KeyBuilder.from(b);

    Arrays.fill(b, (byte) 255);
    Key k255 = KeyBuilder.from(b);

    Set<Key> keys = new LinkedHashSet<>();
    keys.add(k3); // 2^24
    keys.add(k4); // 2^32
    keys.add(k1); // 2^16
    keys.add(k2); // 2^8

    // Insertion order: 3, 4, 1, 2
    Iterator<Key> iter = keys.iterator();
    Assert.assertEquals(iter.next(), k3);
    Assert.assertEquals(iter.next(), k4);
    Assert.assertEquals(iter.next(), k1);
    Assert.assertEquals(iter.next(), k2);

    SortedSet<Key> sortedKeys = new TreeSet<>(new KeyDistanceComparator(k0));
    sortedKeys.addAll(keys);

    iter = sortedKeys.iterator();
    Assert.assertEquals(iter.next(), k1);
    Assert.assertEquals(iter.next(), k2);
    Assert.assertEquals(iter.next(), k3);
    Assert.assertEquals(iter.next(), k4);

    // Distance from 2^160: 4, 3, 2, 1
    sortedKeys = new TreeSet<>(new KeyDistanceComparator(k255));
    sortedKeys.addAll(keys);

    iter = sortedKeys.iterator();
    Assert.assertEquals(iter.next(), k4);
    Assert.assertEquals(iter.next(), k3);
    Assert.assertEquals(iter.next(), k2);
    Assert.assertEquals(iter.next(), k1);
  }
}
