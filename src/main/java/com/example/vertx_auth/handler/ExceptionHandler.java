package com.example.vertx_auth.handler;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ExceptionHandler implements Handler<Throwable> {

  private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

  @Override
  public void handle(Throwable event) {
    log.error(event);
  }
}
