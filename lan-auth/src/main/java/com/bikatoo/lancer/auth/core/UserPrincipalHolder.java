package com.bikatoo.lancer.auth.core;

import static com.bikatoo.lancer.common.utils.PreconditionUtil.checkConditionAndThrow;

import com.bikatoo.lancer.auth.permission.PermissionEnum;
import com.bikatoo.lancer.cache.core.user.UserCache;
import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.utils.MD5Utils;
import com.bikatoo.lancer.common.utils.RequestUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class UserPrincipalHolder implements PrincipalHolder {

  @Resource
  private UserCache userCache;

  @Override
  public Principal getPrincipal() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));

    Long id = getPrincipalId();
    return new UserPrincipal(id, RequestUtils.getTokenInRequest());
  }

  public Long getUserId() {
    return getPrincipal().getId();
  }

  @Override
  public boolean valid() {
    String token = RequestUtils.getTokenInRequest();
    if (token == null) {
      return false;
    }
    return userCache.getUserByToken(token) != null;
  }

  @Override
  public Principal register(Long principalId, String phoneNumber, Set<PermissionEnum> permissions) {

    final String token = MD5Utils.md5(phoneNumber, String.valueOf(System.currentTimeMillis()));
    userCache.cacheToken2userId(token, principalId);
    return new UserPrincipal(principalId, token);
  }

  @Override
  public void release() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));

    String token = RequestUtils.getTokenInRequest();
    userCache.remove(token);
  }

  @Override
  public Long getPrincipalId() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));
    String token = RequestUtils.getTokenInRequest();
    return userCache.getUserByToken(token);
  }

  @Override
  public PrincipalType getPrincipalType() {
    return PrincipalType.USER;
  }

  @Override
  public String getIp() {
    return RequestUtils.getIp();
  }

  @Override
  public boolean hasAnyPermission(PermissionEnum... permissions) {
    return false;
  }
}
