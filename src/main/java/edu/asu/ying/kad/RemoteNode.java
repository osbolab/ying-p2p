package edu.asu.ying.kad;

import com.google.common.util.concurrent.ListenableFuture;

import edu.asu.ying.kad.rpc.PingStatistics;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface RemoteNode extends KadNode {

  ListenableFuture<PingStatistics> ping();
}
