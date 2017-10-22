package cn.zzx.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Json builder.
 *
 * @author fzh
 * @version 1.0
 * @since 2017 /8/29
 */
public class JsonWrapperBuilder {
    private static final String INITIAL_MSG = "INITIAL_MSG" + System.currentTimeMillis();
    private static final int INITIAL_STATUS = -200;
    private JsonWrapper result = null;
    private int status = INITIAL_STATUS;
    private String msg = INITIAL_MSG;

    public JsonWrapperBuilder() {
        this.result = new JsonWrapper();
    }

    public JsonWrapperBuilder setStatus(int status) {
        this.status = status;
        result.setStatus(status);
        return this;
    }

    public JsonWrapperBuilder setMsg(String msg) {
        this.msg = msg;
        result.setMsg(msg);
        return this;
    }

    public JSONObject build() {
        if (this.status == INITIAL_STATUS) {
            result.setStatus(INITIAL_STATUS);
        }
        if (INITIAL_MSG.equals(msg)) {
            result.setMsg(INITIAL_MSG);
        }
        try {
            Method checkNull = result.getClass().getDeclaredMethod("dataEmpty");

            checkNull.setAccessible(true);
            if (Boolean.TRUE.equals(checkNull.invoke(result))) {
                result.setData(new JSONObject());
            }
        } catch (NoSuchMethodException | InvocationTargetException e) {
            // jdk 1.8 才可以把异常折叠起来
            e.printStackTrace();
            // 防止修改掉这个方法
        } catch (IllegalAccessException e) {
            // 权限异常
        }
        return result.get();
    }

    public JsonWrapperBuilder setDataEntries(Map<String, ?> map) {
        result.setDataEntries(map);
        return this;
    }

    public JsonWrapperBuilder setDataEntry(String key, Object obj) {
        result.setDataEntry(key, obj);
        return this;
    }

    public JsonWrapperBuilder setDataObject(Object object) {
        result.setDataObject(object);
        return this;
    }

    public JsonWrapperBuilder setDataObject(String key, Object object) {
        result.setDataObject(key, object);
        return this;
    }

    public JsonWrapperBuilder setDataObjects(Collection<?> objects) {
        result.setDataObjects(objects);
        return this;
    }

    public JsonWrapperBuilder setDataJsonObject(String key, JSONObject object) {
        result.setDataJsonObject(key, object);
        return this;
    }

    public JsonWrapperBuilder setDataJsonArray(String key, JSONArray array) {
        result.setDataJsonArray(key, array);
        return this;
    }

    public JsonWrapperBuilder setDataObjects(Map<String, ?> map) {
        result.setDataObjects(map);
        return this;
    }

    public JsonWrapperBuilder setDataArray(String key, Collection<?> objects) {
        result.setDataArray(key, objects);
        return this;
    }

    public JsonWrapperBuilder setDataArrays(Map<String, Collection<?>> map) {
        result.setDataArrays(map);
        return this;
    }

    public JsonWrapperBuilder setDataMapArray(String key, Collection<Map<?, ?>> value) {
        result.setDataMapArray(key, value);
        return this;
    }
}