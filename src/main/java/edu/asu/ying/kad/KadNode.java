package edu.asu.ying.kad;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface KadNode {

  Key key();

  /**
   * For the local node this represents the local interface and port that node is listening on.
   * For a remote node this represents the address and port it can be reached at.
   */
  Endpoint endpoint();
}
