package com.example.vertx_auth;

import com.example.vertx_auth.handler.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FormLoginHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class MainVerticle extends AbstractVerticle {

  private AuthProvider authProvider = new CustomAuthProvider();
  private TemplateEngine templateEngine = templateEngine();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/")
      .handler(new LoginPageHandler(templateEngine));
    router.route("/admin")
      .handler(new DebugHandler())
//      .handler(FormLoginHandler.create(authProvider).setDirectLoggedInOKURL("admin"))
      .handler(new FormAuthhandler())
      .handler(BasicAuthHandler.create(authProvider))
      .handler(new AdminPageHandler(templateEngine))
    ;
    router.route("/logout").handler(new LogoutPageHandler(templateEngine));

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

  private TemplateEngine templateEngine() {
    TemplateEngine templateEngine = new TemplateEngine();

    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/");
    resolver.setSuffix(".html");
    resolver.setOrder(1);
    resolver.setCacheable(true);

    templateEngine.setTemplateResolver(resolver);

    return templateEngine;
  }

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }
}
