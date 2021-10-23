package com.github.huluvu424242;

import java.util.Optional;

/**
 * Cache, for storing data associated with keys.
 */
public interface Cache {

    /**
     * Store a value object in the cache with no specific expiry time. The object may be evicted by the cache any time,
     * if necessary.
     *
     * @param key   key that may be used to retrieve the object in the future
     * @param value the value object to be stored
     */
    void put(String key, String value);

    /**
     * Retrieve a value object from the cache.
     * @param key               the key that was used to insert the object initially
     * @return                  the object if it was in the cache, or an empty Optional if not found.
     */
    Optional<String> get(String key);
}