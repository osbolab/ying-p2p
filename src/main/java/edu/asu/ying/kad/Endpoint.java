package edu.asu.ying.kad;

import java.net.InetAddress;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface Endpoint {

  InetAddress inetAddress();

  int port();
}
