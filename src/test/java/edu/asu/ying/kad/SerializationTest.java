package edu.asu.ying.kad;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.asu.ying.kad.messages.AckMessage;
import edu.asu.ying.kad.messages.FindNodesMessage;
import edu.asu.ying.kad.messages.FindValueMessage;
import edu.asu.ying.kad.messages.FoundNodesMessage;
import edu.asu.ying.kad.messages.FoundValueMessage;
import edu.asu.ying.kad.messages.MessageType;
import edu.asu.ying.kad.messages.Messages;
import edu.asu.ying.kad.messages.StoreMessage;
import edu.asu.ying.kad.messages.SynMessage;
import edu.asu.ying.kad.messages.serialization.MessageInputStream;
import edu.asu.ying.kad.messages.serialization.MessageOutputStream;

/**
 * @author matt@osbolab.com (Matt Barnard)
 */
@RunWith(JUnit4.class)
public final class SerializationTest {

  private static final int id = 1234;
  private static final int port = 12345;
  private static final Key key = KeyBuilder.random();

  @Test
  public void synMessage() throws IOException {
    SynMessage msgOut = Messages.syn(id, key, port);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(SynMessage.TYPE, type);
        SynMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void ackMessage() throws IOException {
    AckMessage msgOut = Messages.ack(id, key, port);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(AckMessage.TYPE, type);
        AckMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void findNodesMessage() throws IOException {
    Key findKey = KeyBuilder.random();
    FindNodesMessage msgOut = Messages.findNodes(id, key, port, findKey);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(FindNodesMessage.TYPE, type);
        FindNodesMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(findKey, msg.findKey());
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void foundNodesMessage() throws IOException {
    Set<RemoteNode> nodes = new HashSet<>();
    nodes.add(new RemoteNodeProxy(KeyBuilder.random(), IpEndpoint.loopback(12345)));
    nodes.add(new RemoteNodeProxy(KeyBuilder.random(), IpEndpoint.loopback(12345)));
    nodes.add(new RemoteNodeProxy(KeyBuilder.random(), IpEndpoint.loopback(12345)));
    nodes.add(new RemoteNodeProxy(KeyBuilder.random(), IpEndpoint.loopback(12345)));
    nodes.add(new RemoteNodeProxy(KeyBuilder.random(), IpEndpoint.loopback(12345)));

    FoundNodesMessage msgOut = Messages.foundNodes(id, key, port, nodes);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(FoundNodesMessage.TYPE, type);
        FoundNodesMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        for (RemoteNode node : msg.nodes()) {
          Assert.assertTrue(nodes.contains(node));
        }
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void findValueMessage() throws IOException {
    Key findKey = KeyBuilder.random();
    FindValueMessage msgOut = Messages.findValue(id, key, port, findKey);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(FindValueMessage.TYPE, type);
        FindValueMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(findKey, msg.findKey());
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void foundValueMessage() throws IOException {
    Key findKey = KeyBuilder.random();
    Random random = new Random();
    byte[] value = new byte[30];
    random.nextBytes(value);
    FoundValueMessage msgOut = Messages.foundValue(id, key, port, findKey, value);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(FoundValueMessage.TYPE, type);
        FoundValueMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(findKey, msg.foundKey());
        Assert.assertTrue(msg.valueEquals(value));
        Assert.assertEquals(msgOut, msg);
      }
    }
  }

  @Test
  public void storeMessage() throws IOException {
    Key storeKey = KeyBuilder.random();
    Random random = new Random();
    byte[] storeValue = new byte[30];
    random.nextBytes(storeValue);
    StoreMessage msgOut = Messages.store(id, key, port, storeKey, storeValue);

    byte[] bytes;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (MessageOutputStream mos = new MessageOutputStream(baos)) {
        mos.write(msgOut);
        bytes = baos.toByteArray();
      }
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      try (MessageInputStream mis = new MessageInputStream(bais)) {
        MessageType type = mis.readType();
        Assert.assertEquals(StoreMessage.TYPE, type);
        StoreMessage msg = mis.read(type);
        Assert.assertEquals(id, msg.id());
        Assert.assertEquals(key, msg.senderKey());
        Assert.assertEquals(port, msg.senderPort());
        Assert.assertEquals(storeKey, msg.storeKey());
        Assert.assertTrue(msg.valueEquals(storeValue));
        Assert.assertEquals(msgOut, msg);
      }
    }
  }
}
