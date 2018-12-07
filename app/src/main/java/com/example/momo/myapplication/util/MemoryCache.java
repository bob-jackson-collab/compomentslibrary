package com.example.momo.myapplication.util;

import android.util.LruCache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/10/26
 *   desc: MyApplication
 * </pre>
 */
public class MemoryCache {

    private final static int MAX_SIZE = 80;

    private final static LruCache<String, Object> cacheMap = new LruCache<>(MAX_SIZE);

    public static void save(String key, Object cache) {
        if (cache instanceof Collection) {
            try {
                Collection collection = (Collection) cache.getClass().newInstance();
                collection.addAll((Collection) cache);
                cache = collection;
            } catch (Exception e) {
            }
        } else if (cache instanceof Map) {
            try {
                Map map = (Map) cache.getClass().newInstance();
                map.putAll((Map) cache);
                cache = map;
            } catch (Exception e) {
            }
        }

        cacheMap.put(key, cache);
    }

    public static void update(String key, Object newContents) {
        Object cache = get(key);
        if (cache == null) {
            if (newContents instanceof Collection) {
                try {
                    cache = newContents.getClass().newInstance();
                } catch (Exception e) {
                }
            } else if (newContents instanceof Map) {
                try {
                    cache = newContents.getClass().newInstance();
                } catch (Exception e) {
                }
            }
        }

        if (cache instanceof Collection && newContents instanceof Collection) {
            try {
                ((Collection) cache).addAll((Collection) newContents);
            } catch (Exception e) {
            }
        } else if (cache instanceof Map && newContents instanceof Map) {
            try {
                ((Map) cache).putAll((Map) newContents);
            } catch (Exception e) {
            }
        } else {
            cache = newContents;
        }

        cacheMap.put(key, cache);

    }

    public static void remove(String key) {
        cacheMap.remove(key);
    }


    public static Object get(String key) {
        Object cache = cacheMap.get(key);
        if (cache != null) {
            if (cache instanceof Collection) {
                try {
                    Collection collection = (Collection) cache.getClass().newInstance();
                    collection.addAll((Collection) cache);
                    cache = collection;
                } catch (Exception e) {
                }
            } else if (cache instanceof Map) {
                try {
                    Map map = (Map) cache.getClass().newInstance();
                    map.putAll((Map) cache);
                    cache = map;
                } catch (Exception e) {
                }
            }
        }
        return cache;
    }

    public static boolean contains(String key) {
        return cacheMap.get(key) != null;
    }

    public static void clearAll() {
        cacheMap.evictAll();
    }


}
