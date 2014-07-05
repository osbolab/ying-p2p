package edu.asu.ying.kad;

import com.google.common.util.concurrent.ListenableFuture;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nullable;

import edu.asu.ying.kad.rpc.PingStatistics;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public final class RemoteNodeProxy implements RemoteNode {
  private final Key key;
  private final Endpoint endpoint;
  private final int hashCode;

  public RemoteNodeProxy(Key key, Endpoint endpoint) {
    this.key = key;
    this.endpoint = endpoint;
    hashCode = 31 * key.hashCode() + endpoint.hashCode();
  }

  @Override
  public ListenableFuture<PingStatistics> ping() {
    throw new NotImplementedException();
  }

  @Override
  public Key key() {
    return key;
  }

  @Override
  public Endpoint endpoint() {
    return endpoint;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RemoteNodeProxy that = (RemoteNodeProxy) o;
    return endpoint.equals(that.endpoint) && key.equals(that.key);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
