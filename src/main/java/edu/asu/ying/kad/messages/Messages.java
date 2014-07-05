package edu.asu.ying.kad.messages;

import java.util.Set;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;
import edu.asu.ying.kad.RemoteNode;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class Messages {

  public static SynMessage syn(int id, Key key, int port) {
    return new SynMessage(id, key, port);
  }

  public static AckMessage ack(int id, Key key, int port) {
    return new AckMessage(id, key, port);
  }

  public static FindNodesMessage findNodes(int id, Key senderKey, int port, Key findKey) {
    return new FindNodesMessage(id, senderKey, port, findKey);
  }

  public static FindValueMessage findValue(int id, Key senderKey, int port, Key findKey) {
    return new FindValueMessage(id, senderKey, port, findKey);
  }

  public static StoreMessage store(int id,
                                   Key senderKey,
                                   int port,
                                   Key storeKey,
                                   byte[] storeValue) {

    return new StoreMessage(id, senderKey, port, storeKey, storeValue);
  }

  public static FoundNodesMessage foundNodes(int id,
                                             Key key,
                                             int port,
                                             Set<RemoteNode> nodes) {

    return new FoundNodesMessage(id, key, port, nodes);
  }

  public static FoundValueMessage foundValue(int id,
                                             Key key,
                                             int port,
                                             Key foundKey,
                                             byte[] value) {

    return new FoundValueMessage(id, key, port, foundKey, value);
  }
}
