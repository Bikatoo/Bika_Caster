package com.bikatoo.lancer.starter.interceptor;

import com.bikatoo.lancer.auth.core.AccountPrincipalHolder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class SystemLoginInterceptor extends BaseInterceptor {

  @Resource private AccountPrincipalHolder accountPrincipalHolder;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
      return true;
    }
    if (accountPrincipalHolder.valid()) {
      return true;
    }
    response.setStatus(401);
    return false;
  }
}
