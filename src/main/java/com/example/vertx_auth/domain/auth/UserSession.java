package com.example.vertx_auth.domain.auth;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.PRNG;
import io.vertx.ext.web.sstore.AbstractSession;

public class UserSession extends AbstractSession {

  public UserSession(Vertx vertx) {
    super(new PRNG(vertx), 60, 10);
  }
}
