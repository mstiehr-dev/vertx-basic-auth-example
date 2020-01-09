package com.example.vertx_auth;

import com.example.vertx_auth.domain.auth.User;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class CustomAuthProvider implements AuthProvider {

  private final static Logger log = LoggerFactory.getLogger(CustomAuthProvider.class);

  private final JsonObject config;
  private final Map<String, User> users = new HashMap<>();

  public CustomAuthProvider() {
    config = config();
    for (Object obj : config.getJsonArray("users").getList()) {
      User user = JsonObject.mapFrom(obj).mapTo(User.class);
      users.put(user.getUsername(), user);
    }
  }

  private JsonObject config() {
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream("config.json");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder content = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        content.append(line);
      }
      br.close();
      return new JsonObject(content.toString());
    } catch (IOException e) {
      log.error("failed to read configuration");
    }

    return new JsonObject();
  }

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<io.vertx.ext.auth.User>> resultHandler) {
    String username = authInfo.getString("username");
    if (users.containsKey(username)) {
      // verify password
      String password = authInfo.getString("password");
      User authorizedUser = users.get(username);
      if (StringUtils.equals(password, authorizedUser.getPassword())) {
        resultHandler.handle(Future.succeededFuture());
        return;
      }
      log.debug("login failed for user " + username);
    } else {
      log.debug("login failed for unknown user " + username);
    }

    resultHandler.handle(Future.failedFuture("permission denied"));
  }
}
