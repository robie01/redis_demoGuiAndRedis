package org.example.storage;

public interface CrudHandler {
    void connect(String host, int port);
    void disconnect();
    void create(String key, String message);
    String read(String key);
    void update(String key, String message);
    String delete(String key);
    void flushAll();
    void publish(String channel, String message);
    void subscribe(String channel, SubListener listener);
    void pSubscribe(String channelPattern, SubListener listener);
}
