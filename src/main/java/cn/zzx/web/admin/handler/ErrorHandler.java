package cn.zzx.web.admin.handler;

import cn.zzx.util.UnifiedResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fzh
 * @since 2017/10/23
 */
@Slf4j
public class ErrorHandler {
    public static void dealInvalidAdminToken(HttpServletResponse response) {
        try {
            write(response, 401, UnifiedResponse.adminTokenError());
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Send error failed.");
            }
        }
    }

    public static void dealInvalidUrlArguments(HttpServletResponse response) {
        try {
            write(response, 400, UnifiedResponse.invalidArguments());
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Send error failed.");
            }
        }
    }

    public static void dealInvalidURL(HttpServletResponse response) {
        try {
            write(response, 404, UnifiedResponse.invalidURL());
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Send error failed.");
            }
        }
    }

    public static void dealInvalidMethod(HttpServletResponse response) {
        try {
            write(response, 405, UnifiedResponse.invalidMethod());
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Send error failed.");
            }
        }
    }

    public static void dealInvalidMediaType(HttpServletResponse response) {
        try {
            write(response, 415, UnifiedResponse.invalidMediaType());
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Send error failed.");
            }
        }
    }

    private static void write(HttpServletResponse response, int status, JSONObject json) throws IOException {
        PrintWriter writer = null;
        response.setStatus(status);
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
