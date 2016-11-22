package com.x.swag.swag.util;

import org.junit.*;
import static org.junit.Assert.*;
/**
 * Created by barke on 2016-03-24.
 */

public class CacheTest {

    Cache<Class<?>, String> cache;

    @Before
    public void setUp() throws Exception {
        cache = Cache.initialize();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitialize() throws Exception {

    }

    @Test
    public void testCache() throws Exception {

        cache.cache(Long.class, "Long");
        cache.cache(Integer.class, "Integer");
        cache.cache(String.class, "String");


        assertEquals(cache.get(String.class), "String");
        assertEquals(cache.get(Long.class), "Long");
        assertEquals(cache.get(Integer.class), "Integer");

    }

    @Test
    public void testGet() throws Exception {
        assertNull(cache.get(Long.class), "Long");
        cache.cache(Long.class, "Long");
        assertEquals(cache.get(Long.class), "Long");

        cache.cache(String.class, "String");
        assertEquals(cache.get(String.class), "String");

        assertNull(cache.get(Integer.class), "Integer");
    }

    @Test
    public void testInvalidate() throws Exception {
        cache.cache(Long.class, "Long");
        assertEquals(cache.get(Long.class), "Long");
        cache.invalidate(Long.class);
        assertNull(cache.get(Long.class));

        cache.cache(Long.class, "Long");
        assertEquals(cache.get(Long.class), "Long");
        cache.invalidate(Long.class);
        assertNull(cache.get(Long.class));
    }
}
