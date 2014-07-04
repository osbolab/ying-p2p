package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

import static edu.asu.ying.kad.messages.MessageType.Syn;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class SynMessage extends KadMessage {
  SynMessage(int id, Key key, int port) {
    super(Syn, id, key, port);
  }
}
