package cn.zzx.web.admin.handler;

import cn.zzx.util.UnifiedResponse;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author fzh
 * @since 2017/10/23
 */
@ControllerAdvice
public class ParameterCastErrorHandler {

    @ExceptionHandler({ClassCastException.class, NumberFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONObject castError() {
        return UnifiedResponse.invalidArguments();
    }
}
