package org.example;

import redis.clients.jedis.Jedis;

public class PubSub implements CrudHandler {

    String host = "127.0.0.1";
    int port = 7000;
    Jedis jedis = new Jedis(host, port);

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public String read(String inputData) {
        return jedis.get(inputData);
    }
}
