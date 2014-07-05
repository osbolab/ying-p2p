package edu.asu.ying.kad.messages;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FindNodesMessage extends KadMessage {

  public static final MessageType TYPE = MessageType.FindNodes;

  private final Key key;
  private final int hashCode;

  FindNodesMessage(int id, Key senderKey, int port, Key findKey) {
    super(TYPE, id, senderKey, port);

    key = findKey;
    hashCode = 31 * super.hashCode() + key.hashCode();
  }

  public Key findKey() {
    return key;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    FindNodesMessage that = (FindNodesMessage) o;
    return key.equals(that.key);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
