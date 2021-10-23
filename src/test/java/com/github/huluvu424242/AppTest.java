package com.github.huluvu424242;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class AppTest {

    protected Jedis jedis;

    @Container
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);


    @BeforeEach
    public void setUp() {
        final String address = redis.getHost();
        final Integer port = redis.getFirstMappedPort();
        jedis = new Jedis(address, port);
    }

    @Test
    @DisplayName("Test RedisBackedCache")
    void testSimplePutAndGet() {
        final RedisBackedCache underTest = new RedisBackedCache(jedis, "mycache");
        underTest.put("test", "example");

        final String retrieved = underTest.get("test").get();
        assertEquals("example", retrieved);
    }

    @Test
    @DisplayName("Test Redis App")
    void testApp() {
        final App underTest = new App(jedis);
        underTest.saveName();

        final List<String> retrieved = underTest.readValues();
        Assertions.assertEquals("Mysql", retrieved.get(0));
        Assertions.assertEquals("Mongodb", retrieved.get(1));
        Assertions.assertEquals("Redis", retrieved.get(2));
        Assertions.assertEquals(3, retrieved.size());
    }
}
