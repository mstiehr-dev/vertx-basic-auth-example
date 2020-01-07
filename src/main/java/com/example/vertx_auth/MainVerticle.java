package com.example.vertx_auth;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.impl.BasicAuthHandlerImpl;

public class MainVerticle extends AbstractVerticle {

  private AuthProvider authProvider = new CustomAuthProvider();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.route("/")
      .handler(BasicAuthHandler.create(authProvider))
      .handler(req -> {
        req.response().setStatusCode(200).end("yay");
      })
    ;

    vertx.createHttpServer()
      .requestHandler(router)
    .listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }
}
