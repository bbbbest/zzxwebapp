package cn.zzx.web.admin.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 这个类用来处理参数值匹配错误时向前台返回错误信息
 * @author fzh
 * @since 2017/10/23
 */
@ControllerAdvice
public class ParameterCastErrorHandler {

    @ExceptionHandler({ClassCastException.class, NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void castError(HttpServletResponse response) {
        ErrorHandler.dealInvalidUrlArguments(response);
    }
}
