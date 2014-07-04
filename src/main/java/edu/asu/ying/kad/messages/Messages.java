package edu.asu.ying.kad.messages;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class Messages {

  public static SynMessage syn(int id, Key key, int port) {
    return new SynMessage(id, key, port);
  }
}
