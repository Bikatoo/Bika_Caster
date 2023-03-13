package com.bikatoo.lancer.auth.core;

import static com.bikatoo.lancer.common.utils.PreconditionUtil.checkConditionAndThrow;

import com.bikatoo.lancer.auth.permission.PermissionEnum;
import com.bikatoo.lancer.cache.core.account.AccountCache;
import com.bikatoo.lancer.cache.core.permission.PermissionCache;
import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.utils.MD5Utils;
import com.bikatoo.lancer.common.utils.RequestUtils;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountPrincipalHolder implements PrincipalHolder {

  @Resource
  private PermissionCache permissionCache;
  @Resource
  private AccountCache accountCache;

  @Override
  public Principal getPrincipal() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));
    Long id = getPrincipalId();
    return new AccountPrincipal(id, RequestUtils.getTokenInRequest());
  }

  @Override
  public boolean valid() {
    String token = RequestUtils.getTokenInRequest();
    if (token == null) {
      return false;
    }
    return accountCache.getAccountIdByToken(token) != null;
  }

  @Override
  public Principal register(Long principalId, String principalName, Set<PermissionEnum> permissions) {

    final String token = MD5Utils.md5(principalName, String.valueOf(System.currentTimeMillis()));
    accountCache.cacheToken2accountId(token, principalId);
    if (CollectionUtils.isNotEmpty(permissions)) {
      permissionCache.cacheAccountId2Permissions(principalId, permissions.stream().map(PermissionEnum::name).collect(Collectors.toSet()));
    }
    return new AccountPrincipal(principalId, token);
  }

  @Override
  public void release() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));

    String token = RequestUtils.getTokenInRequest();
    accountCache.remove(token);
  }

  @Override
  public Long getPrincipalId() {
    checkConditionAndThrow(valid(), new GlobalException("未登录"));

    String token = RequestUtils.getTokenInRequest();
    return accountCache.getAccountIdByToken(token);
  }

  public Long getAccountId() {
    return getPrincipalId();
  }

  @Override
  public PrincipalType getPrincipalType() {
    return PrincipalType.ACCOUNT;
  }

  @Override
  public String getIp() {
    return RequestUtils.getIp();
  }

  @Override
  public boolean hasAnyPermission(PermissionEnum... permissions) {

    if (permissions == null || permissions.length <= 0) {
      return true;
    }
    Long accountId = getPrincipalId();
    Set<PermissionEnum> permissionSet = permissionCache.getPermissionsByAccountId(accountId)
        .stream().map(PermissionEnum::build).filter(Objects::nonNull).collect(Collectors.toSet());
    return permissionSet.containsAll(Arrays.stream(permissions).collect(Collectors.toSet()));
  }
}
