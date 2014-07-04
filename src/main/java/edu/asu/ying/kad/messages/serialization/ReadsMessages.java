package edu.asu.ying.kad.messages.serialization;

import java.io.IOException;

import edu.asu.ying.kad.messages.KadMessage;
import edu.asu.ying.kad.messages.MessageType;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public interface ReadsMessages {

  MessageType readType() throws IOException;

  <T extends KadMessage> T read(MessageType type) throws IOException;
}
