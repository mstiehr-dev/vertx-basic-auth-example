package com.example.vertx_auth.handler;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;

import java.util.Base64;

public class FormAuthhandler extends AuthHandlerImpl {
  public FormAuthhandler() {
    super(null);
  }

  // take form data and pass as authInfo to next handler


  @Override
  public void parseCredentials(RoutingContext context, Handler<AsyncResult<JsonObject>> handler) {
    final MultiMap params = context.request().params();
    if (params.contains("username") && params.contains("password")) {
      String basic = params.get("username") + ":" + params.get("password");
      String encoded = Base64.getEncoder().encodeToString(basic.getBytes());
      context.request().headers().add("Authorization", "Basic " + encoded);
    }
    context.next();
  }
}
