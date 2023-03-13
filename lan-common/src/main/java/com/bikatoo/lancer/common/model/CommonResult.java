package com.bikatoo.lancer.common.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private String hint;
    private T data;

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<>(200, "", "", data);
    }

    public static <T> CommonResult<T> ok() {
        return new CommonResult<>(200, "", "", null);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, message, "", null);
    }

    public static <T> CommonResult<T> error(Integer code, String message, String hint) {
        return new CommonResult<>(code, message, hint, null);
    }
}
