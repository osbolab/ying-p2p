package edu.asu.ying.kad.messages.serialization;

import java.io.IOException;

import edu.asu.ying.kad.messages.AckMessage;
import edu.asu.ying.kad.messages.FindNodesMessage;
import edu.asu.ying.kad.messages.FindValueMessage;
import edu.asu.ying.kad.messages.FoundNodesMessage;
import edu.asu.ying.kad.messages.FoundValueMessage;
import edu.asu.ying.kad.messages.StoreMessage;
import edu.asu.ying.kad.messages.SynMessage;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface WritesMessages {
  public void write(SynMessage message) throws IOException;

  public void write(AckMessage message) throws IOException;

  public void write(FindValueMessage message) throws IOException;

  public void write(FoundValueMessage message) throws IOException;

  public void write(FindNodesMessage message) throws IOException;

  public void write(FoundNodesMessage message) throws IOException;

  public void write(StoreMessage message) throws IOException;
}
