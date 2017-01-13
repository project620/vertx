/**
*Jan 10, 2017, jim.huang, create
*/
// Copyright (c) 1998-2017 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2017-XX-XX, jim.huang, creation
// ============================================================================
package com.vertx3.handler.restaurant;

import org.springframework.stereotype.Component;

import com.vertx3.constants.Api;
import com.vertx3.handler.basic.AbstractHandler;

import io.vertx.ext.web.RoutingContext;

/**
 * @author jim.huang
 */
@Component(Api.user_signUp)
public class SignUp extends AbstractHandler {
    /*
     * (non-Javadoc)
     * @see io.vertx.core.Handler#handle(java.lang.Object)
     */
    @Override
    public void handle(final RoutingContext event) {
        // TODO Auto-generated method stub

    }

}
