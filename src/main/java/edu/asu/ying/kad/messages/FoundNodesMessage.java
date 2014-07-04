package edu.asu.ying.kad.messages;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import javax.annotation.concurrent.Immutable;

import edu.asu.ying.kad.Key;
import edu.asu.ying.kad.RemoteNode;

import static edu.asu.ying.kad.messages.MessageType.FoundNodes;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class FoundNodesMessage extends KadMessage {
  private final ImmutableSet<RemoteNode> nodes;

  FoundNodesMessage(int id, Key key, int port, Set<RemoteNode> nodes) {
    super(FoundNodes, id, key, port);

    this.nodes = ImmutableSet.copyOf(nodes);
  }

  public ImmutableSet<RemoteNode> nodes() {
    return nodes;
  }
}
