package com.github.huluvu424242;

import java.util.List;
import java.util.Set;
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
        // Get the stored data and print it
        List<String> list = jedis.lrange("tutorial-list", 0, 5);

        for (String s : list) {
            printMessage("Stored string in redis:: " + s);
        }
    }

    public Set<String> readValues() {
        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis();
        printMessage("Connection to server sucessfully");
        //store data in redis list
        // Get the stored data and print it
        final Set<String> list = jedis.keys("*");

        for (String s : list) {
            printMessage("List of stored keys:: " + s);
        }
        return list;
    }

    protected void printMessage(String message) {
        System.out.println(message);
    }
}
