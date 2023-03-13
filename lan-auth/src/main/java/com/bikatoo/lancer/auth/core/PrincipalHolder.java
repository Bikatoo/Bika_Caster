package com.bikatoo.lancer.auth.core;


import com.bikatoo.lancer.auth.permission.PermissionEnum;

import java.util.Set;

public interface PrincipalHolder {

  boolean valid();

  Principal register(Long principalId, String principalName, Set<PermissionEnum> permissions);

  void release();

  Long getPrincipalId();

  PrincipalType getPrincipalType();

  Principal getPrincipal();

  String getIp();

  boolean hasAnyPermission(PermissionEnum... permissions);

  default boolean hasPermission(PermissionEnum permission) {
    return hasAnyPermission(permission);
  }

}
