package cn.zzx.web.admin.interceptor;

import cn.zzx.util.JsonWebTokenUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.zzx.web.admin.handler.ErrorHandler.dealInvalidAdminToken;

/**
 * @author fzh
 * @since 2017/10/22
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (JsonWebTokenUtil.availableJWT(request.getHeader(AUTHORIZATION))) {
            return true;
        }
        dealInvalidAdminToken(response);
        return false;
    }
}
