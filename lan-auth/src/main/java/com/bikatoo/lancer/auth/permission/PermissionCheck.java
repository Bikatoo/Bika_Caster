package com.bikatoo.lancer.auth.permission;

import com.bikatoo.lancer.auth.core.PrincipalType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    PermissionEnum[] permissions();

    PrincipalType type();
}