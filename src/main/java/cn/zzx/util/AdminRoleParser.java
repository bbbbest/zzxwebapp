package cn.zzx.util;

import cn.zzx.bean.AdminRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author fzh
 * @since 2017/10/22
 */
public class AdminRoleParser {
    public static JSONObject parse(AdminRole role) throws IllegalAccessException {
        JSONObject result = new JSONObject();
        result.put("privilegeId", role.getAdminRoleId());
        Field[] fields = AdminRole.class.getDeclaredFields();
        String fieldName;
        String key;
        JSONObject tmp;
        for (Field field : fields) {
            field.setAccessible(true);
            fieldName = field.getName();
            if (fieldName.endsWith("Query")) {
                key = fieldName.substring(0, fieldName.length() - 5);
                if (result.containsKey(key)) {
                    ((JSONObject) result.get(key)).put("query", field.get(role));
                } else {
                    tmp = new JSONObject();
                    tmp.put("query", field.get(role));
                    result.put(key, tmp);
                }
            } else if (fieldName.endsWith("Update")) {
                key = fieldName.substring(0, fieldName.length() - 6);
                if (result.containsKey(key)) {
                    ((JSONObject) result.get(key)).put("update", field.get(role));
                } else {
                    tmp = new JSONObject();
                    tmp.put("update", field.get(role));
                    result.put(key, tmp);
                }
            }
        }
        return result;
    }

    public static JSONArray parse(List<AdminRole> roles) throws IllegalAccessException {
        JSONArray ja = new JSONArray();
        for (AdminRole role : roles) {
            ja.add(parse(role));
        }
        return ja;
    }

    public static JSONArray parse(AdminRole... roles) throws IllegalAccessException {
        return parse(Arrays.asList(roles));
    }
}
