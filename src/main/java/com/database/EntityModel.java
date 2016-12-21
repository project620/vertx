//Dec 21, 2016, jim.huang, create
//
// Copyright (c) 1998-2016 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2016-XX-XX, jim.huang, creation
// ============================================================================
package com.database;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;

/**
 * @author jim.huang
 */
public class EntityModel {
    private static Vertx vertx = Vertx.vertx();

    public static Boolean updateEntity(final Object entity, final Handler<Message<JsonArray>> result) {
        return null;
    }
}
