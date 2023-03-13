package com.bikatoo.lancer.auth.core;

public interface Principal {

  PrincipalType getType();

  Long getId();

  String getToken();

}
