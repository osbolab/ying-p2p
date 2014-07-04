package edu.asu.ying.kad.rpc;

import com.google.common.util.concurrent.ListenableFuture;

import edu.asu.ying.kad.RemoteNode;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface PingsNodes {

  ListenableFuture<PingStatistics> ping(RemoteNode node);
}
