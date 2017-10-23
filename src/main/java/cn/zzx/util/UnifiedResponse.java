package cn.zzx.util;

import net.sf.json.JSONObject;
import org.jetbrains.annotations.Contract;

/**
 * @author fzh
 * @since 2017/10/23
 */
public final class UnifiedResponse {
    private static final JSONObject ADMIN_TOKEN_ERROR;
    private static final JSONObject INVALID_ARGUMENTS;
    private static final JSONObject INVALID_URL;
    private static final JSONObject INVALID_METHOD;
    private static final JSONObject INVALID_MEDIA_TYPE;
    private static final JSONObject SUCCESS_EMPTY;
    private static final JSONObject FAILED_EMPTY;

    static {
        ADMIN_TOKEN_ERROR = JsonWrapper.builder()
                .setStatus(400)
                .setMsg("Authorization checked failed. [reason: Invalid token]")
                .build();

        INVALID_ARGUMENTS = JsonWrapper.builder()
                .setStatus(400)
                .setMsg("Request failed. [reason: Invalid request format]")
                .build();

        INVALID_URL = JsonWrapper.builder()
                .setStatus(404)
                .setMsg("Request failed. [reason: Invalid url]")
                .build();

        INVALID_METHOD = JsonWrapper.builder()
                .setStatus(405)
                .setMsg("Request failed. [reason: Invalid method]")
                .build();

        INVALID_MEDIA_TYPE = JsonWrapper.builder()
                .setStatus(415)
                .setMsg("Request failed. [reason: Unsupported media type]")
                .build();

        SUCCESS_EMPTY = JsonWrapper.builder()
                .setStatus(200)
                .setMsg("success")
                .build();

        FAILED_EMPTY = JsonWrapper.builder()
                .setStatus(415)
                .setMsg("failed")
                .build();
    }

    @Contract(pure = true)
    public static JSONObject adminTokenError() {
        return ADMIN_TOKEN_ERROR;
    }

    @Contract(pure = true)
    public static JSONObject invalidArguments() {
        return INVALID_ARGUMENTS;
    }

    @Contract(pure = true)
    public static JSONObject invalidURL() {
        return INVALID_URL;
    }

    @Contract(pure = true)
    public static JSONObject invalidMethod() {
        return INVALID_METHOD;
    }

    @Contract(pure = true)
    public static JSONObject invalidMediaType() {
        return INVALID_MEDIA_TYPE;
    }

    @Contract(pure = true)
    public static JSONObject successEmptyResp() {
        return SUCCESS_EMPTY;
    }

    @Contract(pure = true)
    public static JSONObject failedEmptyResp() {
        return FAILED_EMPTY;
    }
}
