package com.bikatoo.lancer.starter.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Log4j2
public class RequestLogInterceptor implements HandlerInterceptor {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("reqStartTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 解决session不一致问题
        Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
        response.addCookie(cookie);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpSession session = request.getSession();
        long reqStartTime = NumberUtils.toLong(session.getAttribute("reqStartTime").toString(), 0L);
        if (reqStartTime > 0) {
            MDC.put("respTime", String.valueOf(System.currentTimeMillis() - reqStartTime));
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder requestStringBuilder = new StringBuilder(request.getRequestURI() + "?");

        if (parameterMap != null) {
            Set<Map.Entry<String, String[]>> parameterEntrySet = parameterMap.entrySet();
            for (Map.Entry<String, String[]> parameterEntry : parameterEntrySet) {
                requestStringBuilder.append(parameterEntry.getKey()).append("=").append(parameterEntry.getValue()[0]).append("&");
            }
        }

        if (session.getAttribute("uuid") != null) {
            requestStringBuilder.append("uuid=").append(session.getAttribute("uuid")).append("&");
        }

        if (requestStringBuilder.charAt(requestStringBuilder.length() - 1) == '&') {
            requestStringBuilder.deleteCharAt(requestStringBuilder.length() - 1);
        }

        String responseJson = "";
        if (request.getAttribute("body") != null) {
            try {
                ObjectMapper objectMapper;
                if (request.getAttribute("objectMapper") != null) {
                    objectMapper = (ObjectMapper) request.getAttribute("objectMapper");
                } else {
                    objectMapper = this.objectMapper;
                }
                responseJson = objectMapper.writeValueAsString(request.getAttribute("body"));
            } catch (JsonProcessingException e) {
                log.error("parse response error", e);
            }
        } else {
            responseJson = "response is null";
        }
        log.info("【request】:" + requestStringBuilder);
        log.info("【response】:" + responseJson);
    }
}
