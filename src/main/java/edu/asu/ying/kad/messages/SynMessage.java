package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class SynMessage extends KadMessage {
  public static final MessageType TYPE = MessageType.Syn;

  SynMessage(int id, Key key, int port) {
    super(TYPE, id, key, port);
  }
}
