package com.bikatoo.lancer.auth.permission;

import static com.bikatoo.lancer.common.utils.PreconditionUtil.checkConditionAndThrow;

import com.bikatoo.lancer.auth.core.AccountPrincipalHolder;
import com.bikatoo.lancer.auth.core.PrincipalType;
import com.bikatoo.lancer.common.exception.GlobalException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class PermissionAspect {

    @Resource
    private AccountPrincipalHolder accountPrincipalHolder;

    /**
     * 检验用户是否拥有权限
     */
    @Before("@annotation(permissionCheck)")
    public void checkPermission(JoinPoint joinPoint, PermissionCheck permissionCheck) {

        PermissionEnum[] permissions = permissionCheck.permissions();
        PrincipalType type = permissionCheck.type();

        boolean pass = false;
        if (type == accountPrincipalHolder.getPrincipalType()) {
            pass = accountPrincipalHolder.hasPermission(PermissionEnum.SYSTEM_ADMIN) ||
                accountPrincipalHolder.hasAnyPermission(permissions);
        }
        checkConditionAndThrow(pass, new GlobalException(10086, "权限不足", "权限不足"));
    }

}
