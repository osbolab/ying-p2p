package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.FindNodes;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FindNodesMessage extends KadMessage {
  private final Key key;

  FindNodesMessage(int id, Key senderKey, int port, Key findKey) {
    super(FindNodes, id, senderKey, port);

    key = findKey;
  }

  public Key findKey() {
    return key;
  }
}
