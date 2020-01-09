package com.example.vertx_auth.handler;

import com.example.vertx_auth.domain.auth.User;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class AdminPageHandler implements Handler<RoutingContext> {

  private TemplateEngine templateEngine;
  private final static String templateName = "admin";
  private final Context thyCtx = new Context();

  public AdminPageHandler(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public void handle(RoutingContext context) {
    String username = ((User) context.user()).getUsername();
    thyCtx.setVariable("username", username);
    String page = templateEngine.process(templateName, thyCtx);
    context.response()
      .setStatusCode(200)
      .end(page);
  }
}
