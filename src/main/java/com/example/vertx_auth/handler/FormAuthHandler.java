package com.example.vertx_auth.handler;

import com.example.vertx_auth.domain.auth.User;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import io.vertx.ext.web.handler.impl.HttpStatusException;

public class FormAuthHandler extends AuthHandlerImpl {
  public FormAuthHandler(AuthProvider authProvider) {
    super(authProvider);
  }

  // take form data and pass as authInfo to next handler


  @Override
  public void parseCredentials(RoutingContext context, Handler<AsyncResult<JsonObject>> handler) {
    final MultiMap params = context.request().params();
    if (params.contains("username") && params.contains("password")) {

      String username = params.get("username");
      String password = params.get("password");

      JsonObject userJson = new JsonObject().put("username", username).put("password", password);
      authProvider.authenticate(userJson, ar -> {
        if (ar.succeeded()) {
          User user = (User) ar.result();
          context.session().put("user", user);
          context.setUser(user);
          handler.handle(Future.succeededFuture(userJson));
        } else {
          handler.handle(Future.failedFuture(new HttpStatusException(401)));
        }
      });
    } else if (context.session() != null && context.session().get("user") != null){
      User user = context.session().get("user");
      context.setUser(user);
      handler.handle(Future.succeededFuture(JsonObject.mapFrom(user)));
    } else {
      context.fail(403);
    }
  }
}
