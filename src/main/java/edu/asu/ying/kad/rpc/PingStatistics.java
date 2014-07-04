package edu.asu.ying.kad.rpc;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface PingStatistics {

  boolean didRespond();

  long latencyMs();
}
