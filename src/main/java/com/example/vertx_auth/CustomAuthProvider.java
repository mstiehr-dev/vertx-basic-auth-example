package com.example.vertx_auth;

import io.netty.util.concurrent.SucceededFuture;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;


public class CustomAuthProvider implements AuthProvider {

  private final static Logger log = LoggerFactory.getLogger(CustomAuthProvider.class);

  // TODO read allowed credentials from configuration file

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
    log.info(authInfo.encodePrettily());

    if ("root".equals(authInfo.getString("username")))
        resultHandler.handle(Future.succeededFuture());
    else
      resultHandler.handle(Future.failedFuture("permission denied"));
  }
}
