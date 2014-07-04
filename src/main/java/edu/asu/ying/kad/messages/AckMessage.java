package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.Ack;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class AckMessage extends KadMessage {
  AckMessage(int id, Key key, int port) {
    super(Ack, id, key, port);
  }
}
