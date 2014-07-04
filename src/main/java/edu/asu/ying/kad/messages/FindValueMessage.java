package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.FindValue;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FindValueMessage extends KadMessage {
  private final Key key;

  FindValueMessage(int id, Key senderKey, int port, Key findKey) {
    super(FindValue, id, senderKey, port);
    this.key = findKey;
  }

  public Key findKey() {
    return key;
  }
}
