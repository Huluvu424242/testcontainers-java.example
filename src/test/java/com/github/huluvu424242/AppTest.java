package com.github.huluvu424242;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class AppTest {

    private RedisBackedCache underTest;

    @Container
    public GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);


    @BeforeEach
    public void setUp() {
        final String address = redis.getHost();
        final Integer port = redis.getFirstMappedPort();
        final Jedis jedis = new Jedis(address, port);

        // Now we have an address and port for Redis, no matter where it is running
        underTest = new RedisBackedCache(jedis, "mycache");
    }

    @Test
    void testSimplePutAndGet() {
        underTest.put("test", "example");

        final String retrieved = underTest.get("test").get();
        assertEquals("example", retrieved);
    }
}
