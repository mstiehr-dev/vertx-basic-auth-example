package com.example.vertx_auth.domain.auth;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

public class User extends AbstractUser {

  private String username;
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User() {
  }

  @Override
  protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> resultHandler) {
    // currently not considered
    resultHandler.handle(Future.succeededFuture());
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public JsonObject principal() {
    // currently not considered
    return new JsonObject();
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {
    // currently not considered
  }
}
