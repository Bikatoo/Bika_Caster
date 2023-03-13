package com.bikatoo.lancer.auth.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal implements Principal {

  private Long id;

  private String token;

  @Override
  public PrincipalType getType() {
    return PrincipalType.USER;
  }
}
