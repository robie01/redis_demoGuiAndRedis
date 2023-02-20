package org.example.storage.impl;

import org.example.storage.CrudHandler;
import org.example.storage.SubListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisClient extends JedisPubSub  implements CrudHandler {
    private Jedis client;
    private Jedis subClient;
    private SubListener listener;

    @Override
    public void connect(String host, int port) {
        client = new Jedis(host, port);
        subClient = new Jedis(host, port);
    }

    @Override
    public void disconnect() {
        client.disconnect();
    }

    @Override
    public void create(String key, String message) {
        client.set(key, message);
    }

    @Override
    public String read(String key) {
        return client.get(key);
    }

    @Override
    public void update(String key, String message) {
        client.set(key, message);
    }

    @Override
    public String delete(String key) {
        client.del(key);
        return key;
    }

    @Override
    public void flushAll() {
        client.flushAll();
    }

    @Override
    public void publish(String channel, String message) {
        client.publish(channel, message);
    }

    @Override
    public void subscribe(String channel, SubListener listener) {
        this.listener = listener;

        subClient.subscribe(this, channel);
    }

    @Override
    public void pSubscribe(String channelPattern, SubListener listener) {
        this.listener = listener;

        subClient.subscribe(this, channelPattern);
    }

    @Override
    public void onMessage(String channel, String message) {
        this.listener.onMessageReceived(channel, message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        this.listener.onMessageReceived(channel, message);
    }
}
