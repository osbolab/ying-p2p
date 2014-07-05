package edu.asu.ying.kad.messages;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;
import edu.asu.ying.kad.RemoteNode;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FoundNodesMessage extends KadMessage {

  public static final MessageType TYPE = MessageType.FoundNodes;

  private final ImmutableSet<RemoteNode> nodes;
  private final int hashCode;

  FoundNodesMessage(int id, Key key, int port, Set<RemoteNode> nodes) {
    super(TYPE, id, key, port);

    this.nodes = ImmutableSet.copyOf(nodes);
    hashCode = 31 * super.hashCode() + nodes.hashCode();
  }

  public ImmutableSet<RemoteNode> nodes() {
    return nodes;
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
    FoundNodesMessage that = (FoundNodesMessage) o;
    return nodes.equals(that.nodes);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
