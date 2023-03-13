package com.bikatoo.lancer.starter.interceptor;

import com.bikatoo.lancer.auth.core.UserPrincipalHolder;
import com.bikatoo.lancer.common.exception.GlobalException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LoginInterceptor extends BaseInterceptor {

    @Resource
    private UserPrincipalHolder userPrincipalHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        if (userPrincipalHolder.valid()) {
            return true;
        }
        throw new GlobalException("未登录");
    }
}
