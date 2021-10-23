package com.github.huluvu424242;

import java.util.List;
import redis.clients.jedis.Jedis;

public class App {

    protected Jedis jedis;

    public App() {
        this(new Jedis("localhost"));
    }

    public App(Jedis jedis) {
        this.jedis = jedis;
    }

    public static void main(String[] args) {
        final App app = new App();
        app.printMessage("Hello World!");
        app.saveName();

        app.readValues();
    }

    public void saveName() {
        //Connecting to Redis server on localhost
        printMessage("Connection to server sucessfully");

        //store data in redis list
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");

    }

    public List<String> readValues() {
        printMessage("Connection to server sucessfully");
        final List<String> list = jedis.lrange("tutorial-list", 0, 5);

        for (String s : list) {
            printMessage("Stored string in redis:: " + s);
        }
        return list;
    }

    protected void printMessage(String message) {
        System.out.println(message);
    }
}
