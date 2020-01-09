package com.example.vertx_auth.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * currently not used as thymeleaf offers login-page ootb
 */
public class LoginPageHandler implements Handler<RoutingContext> {

  private final TemplateEngine templateEngine;
  private final String templateName = "login";
  private final Context thyCtx = new Context();

  public LoginPageHandler(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public void handle(RoutingContext context) {
    String page = templateEngine.process(templateName, thyCtx);
    context.response().setStatusCode(200).end(page);
  }
}
