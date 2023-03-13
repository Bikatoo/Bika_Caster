package com.bikatoo.lancer.starter.interceptor;

import com.bikatoo.lancer.common.singleton.SingletonObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class BaseInterceptor implements HandlerInterceptor {

    /**
     * 将某个对象转换成json格式并发送到客户端
     */
    protected void sendJsonMessage(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        request.setAttribute("body", obj);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper om = SingletonObjectMapper.OM.getOm();
        writer.print(om.writeValueAsString(obj));
        writer.close();
        response.flushBuffer();
    }
}
