package com.bikatoo.lancer.starter.interceptor;

import com.bikatoo.lancer.common.exception.GlobalException;
import com.bikatoo.lancer.common.model.CommonResult;
import com.bikatoo.lancer.common.utils.RequestUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public Object handleException(GlobalException e) {
        CommonResult<Object> resp = CommonResult.error(e.getStatus(), e.getMessage(), e.getUserHint());
        RequestUtils.getRequest().setAttribute("body", resp);
        return resp;
    }
}
