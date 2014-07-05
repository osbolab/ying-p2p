package edu.asu.ying.kad;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@Immutable
public final class IpEndpoint implements Endpoint {

  private final InetAddress address;
  private final int port;
  private final int hashCode;

  public IpEndpoint(InetAddress address, int port) {
    this.address = address;
    this.port = port;
    hashCode = 31 * port + address.hashCode();
  }

  public static Endpoint loopback(int port) throws UnknownHostException {
    return new IpEndpoint(InetAddress.getLoopbackAddress(), port);
  }

  @Override
  public InetAddress inetAddress() {
    return address;
  }

  @Override
  public int port() {
    return port;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IpEndpoint that = (IpEndpoint) o;
    return port == that.port && address.equals(that.address);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }
}
