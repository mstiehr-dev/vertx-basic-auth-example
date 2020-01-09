package com.example.vertx_auth.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class LogoutPageHandler implements Handler<RoutingContext> {

  private final TemplateEngine templateEngine;
  private final String templateName = "logout";
  private final Context thyCtx = new Context();

  public LogoutPageHandler(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public void handle(RoutingContext context) {
    String page = templateEngine.process(templateName, thyCtx);
    context.response().setStatusCode(401).end(page);
  }
}
