package edu.asu.ying.kad;

import java.util.Set;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface LocalNode extends KadNode {

  void start();

  void join(Set<Endpoint> bootstrap);

  void stop();

  /**
   * Locates the {@code count} nodes closest to the specified key by initiating a search from this
   * node.
   */
  RemoteNode findNodes(Key key, int count);
}
