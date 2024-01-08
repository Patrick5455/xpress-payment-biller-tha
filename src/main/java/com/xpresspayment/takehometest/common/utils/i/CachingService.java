package com.xpresspayment.takehometest.common.utils.i;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xpresspayment.takehometest.common.exceptions.AppException;
import org.springframework.stereotype.Service;

@Service
public abstract class CachingService<K, V> {

    /**
     * @apiNote : default implementation uses concurrent hash map
     */
    protected final Map<K, V> cache = new ConcurrentHashMap<>();

    /**
     *
     * @param k
     * @param defaultValue
     * @return
     */
    public V getOrDefault(K k, V defaultValue) {
        if(!cache.containsKey(k)) {
            return defaultValue;
        }
        return cache.get(k);
    };

    /**
     * *
     * @param k
     * @param defaultValue
     * @param deserializeTo
     * @return
     */
    public V getOrDefault(K k, V defaultValue, Class<?> deserializeTo) {
        throw new AppException("no cache implementation found");
    };

    /**
     * @apiNote does not support null values
     * @param key
     * @param value
     * @throws AppException
     */
    public void saveOrUpdate(K key, V value) throws AppException {
        if (value == null) {
            throw new AppException("cannot update cache with null values");
        }
        cache.put(key, value);
    }

    /***
     * *
     * @param key
     * @param value
     * @param serializeFrom
     * @throws AppException
     */
    public void saveOrUpdate(K key, V value, Class<?> serializeFrom) throws AppException {
        throw new AppException("no cache implementation found");
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public long expireKey (K key, int seconds) {
        return seconds;
    }

    public boolean contains(K key) {
        return cache.containsKey(key);
    }
}
