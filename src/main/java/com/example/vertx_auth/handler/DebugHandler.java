package com.example.vertx_auth.handler;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;


public class DebugHandler implements Handler<RoutingContext> {

  private final Logger log = LoggerFactory.getLogger(DebugHandler.class);

  @Override
  public void handle(RoutingContext context) {
    log.info(context);
    context.next();
  }
}
