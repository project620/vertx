//Jan 9, 2017, jim.huang, create
//
// Copyright (c) 1998-2017 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2017-XX-XX, jim.huang, creation
// ============================================================================
package com.vertx3.cache;

/**
 * @author jim.huang
 */
public interface CacheManager {

    public <T> T get(String key, Class<T> keyClass);

    public void set(String key, Object value);

    public void delete(String key);

    public void add(String key, Object object);

}
