package com.x.swag.swag.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by barke on 2016-03-24.
 */
public class Cache <K, V> {
    private final Map<K, V> storage;

    private <K, V> Cache(){
        storage = new HashMap<>();
    }

    public static <K, V> Cache<K, V> initialize(){
        return new Cache();
    }

    public void cache(K key, V value){
        storage.put(key, value);
    }

    public V get(K key){
        return storage.get(key);
    }


    public void invalidate(K key){
        storage.remove(key);
    }

}
