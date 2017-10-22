package cn.zzx.web.admin.interceptor;

import cn.zzx.util.JsonWebTokenUtil;
import cn.zzx.util.JsonWrapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fzh
 * @since 2017/10/22
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (JsonWebTokenUtil.availableJWT(request.getHeader("Authorization"))) {
            return true;
        }
        dealInvalidToken(request, response, JsonWrapper.builder().setStatus(400).setMsg("validate failed, invalid token!").build());
        return false;
    }

    private void dealInvalidToken(HttpServletRequest request, HttpServletResponse response, JSONObject json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException ex) {
            log.error("response error", ex);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
