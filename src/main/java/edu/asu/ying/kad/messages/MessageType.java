package edu.asu.ying.kad.messages;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
public enum MessageType {
  Syn((byte) 0),
  FindNodes((byte) 1),
  FindValue((byte) 2),
  Store((byte) 3),

  Ack((byte) 127),
  FoundNodes((byte) 128),
  FoundValue((byte) 129);

  public static MessageType fromValue(int i) {
    for (MessageType type : values()) {
      if (type.toByte() == i) {
        return type;
      }
    }
    return null;
  }

  private final byte value;

  private MessageType(byte value) {
    this.value = value;
  }

  public byte toByte() {
    return value;
  }
}
