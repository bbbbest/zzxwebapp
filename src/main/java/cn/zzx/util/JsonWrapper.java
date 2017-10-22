package cn.zzx.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 依赖net.sf.json
 *
 * @author fzh
 * @since 2017/8/31
 */
public class JsonWrapper {
    private JSONObject result = null;
    private static final JSONObject EMPTY_JSON_OBJECT = new JSONObject();
    private JSONObject data = EMPTY_JSON_OBJECT;

    public static JsonWrapperBuilder builder() {
        return new JsonWrapperBuilder();
    }

    public JsonWrapper() {
        this.result = new JSONObject();
    }

    /**
     * 根节点添加键值对
     *
     * @param key   键
     * @param value 值
     */
    public void setEntry(String key, Object value) {
        result.put(key, value);
    }

    /**
     * 根节点添加JSONObject
     *
     * @param key        键
     * @param jsonObject jsonObject对象
     */
    public void setObject(String key, JSONObject jsonObject) {
        result.put(key, jsonObject);
    }

    /**
     * 根节点添加pojo对象，键的名字默认为类名的小写
     *
     * @param pojo pojo对象
     */
    public void setObject(Object pojo) {
        setObjectForObject(result, pojo);
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param key  键
     * @param pojo pojo对象
     */
    public void setObject(String key, Object pojo) {
        setObjectForObject(result, key, pojo);
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param pojos pojo数组
     */
    public void setObjects(Object[] pojos) {
        setObjectsForObject(result, Arrays.asList(pojos));
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param pojos pojo集合
     */
    public void setObjects(Collection<?> pojos) {
        setObjectsForObject(result, pojos);
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param pojosMap pojo的map对象，map的键即为pojo对象对应的值
     */
    public void setObjects(Map<String, ?> pojosMap) {
        setObjectsForObject(result, pojosMap);
    }

    /**
     * 为根节点添加jsonArray
     *
     * @param key       键
     * @param jsonArray json数组
     */
    public void setArray(String key, JSONArray jsonArray) {
        result.put(key, jsonArray);
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param key   键
     * @param pojos pojo对象集合
     */
    public void setArray(String key, Collection<?> pojos) {
        setArrayForObject(result, key, pojos);
    }

    /**
     * 为根节点添加pojo对象
     *
     * @param pojos pojo对象的map，map的键将作为其对应的value在根节点中的键
     */
    public void setArrays(Map<String, Collection<?>> pojos) {
        setArraysForObject(result, pojos);
    }

    /**
     * 辅助函数，为根节点增加一个子对象
     *
     * @param key         键
     * @param jsonWrapper jsonResult对象
     */
    public void setChild(String key, JsonWrapper jsonWrapper) {
        result.put(key, jsonWrapper.get());
    }

    public void setChild(String key, JSONObject jsonObject) {
        result.put(key, jsonObject);
    }

    public void setChild(String key, JSONArray jsonArray) {
        result.put(key, jsonArray);
    }

    public void setParent(String key, JSONObject jsonObject) {
        jsonObject.put(key, result);
    }

    public void setParent(JSONArray jsonArray) {
        jsonArray.add(result);
    }

    public void setParent(String key, JsonWrapper jsonWrapper) {
        jsonWrapper.get().put(key, result);
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        result.put("status", status);
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        result.put("msg", msg);
    }

    public void setData(JSONObject data) {
        result.put("data", data);
    }

    /**
     * 返回处理后的json数据
     *
     * @return json 对象
     */
    public JSONObject get() {
        if (!dataEmpty()) {
            result.put("data", data);
        }
        return result;
    }

    /**
     * 为Data设置多个键值对，注意值的类型
     *
     * @param map the map
     */
    public void setDataEntries(Map<String, ?> map) {
        requireDataNotNull();
        data.putAll(map);
    }

    /**
     * 为Data设置键值对，注意值的类型
     *
     * @param obj the value
     */
    public void setDataEntry(String key, Object obj) {
        requireDataNotNull();
        data.put(key, obj);
    }

    public void setDataObject(Object pojo) {
        requireDataNotNull();
        setObjectForObject(data, pojo);
    }

    public void setDataObject(String key, Object pojo) {
        requireDataNotNull();
        setObjectForObject(data, key, pojo);
    }

    /**
     * 1、pojo 为简单的pojo对象
     * 2、默认键的名字为类名的小写
     * 3、如果类是重复的，那么后面添加的数据就会覆盖掉前面的数据
     *
     * @param pojos 需要转化的pojo对象集合
     */
    public void setDataObjects(Collection<?> pojos) {
        requireDataNotNull();
        setObjectsForObject(data, pojos);
    }

    public void setDataJsonObject(String key, JSONObject jsonObject) {
        requireDataNotNull();
        data.put(key, jsonObject);

    }

    public void setDataJsonArray(String key, JSONArray jsonArray) {
        requireDataNotNull();
        data.put(key, jsonArray);
    }

    /**
     * 1、pojo 为简单的pojo对象
     * 2、会默认把所有的属性加载进来
     *
     * @param pojoMap the map
     */
    public void setDataObjects(Map<String, ?> pojoMap) {
        requireDataNotNull();
        setObjectsForObject(data, pojoMap);
    }

    /**
     * 为data设置json数组 （一个）
     *
     * @param key   the key
     * @param pojos pojo对象的集合
     */
    public void setDataArray(String key, Collection<?> pojos) {
        requireDataNotNull();
        setArrayForObject(data, key, pojos);
    }

    /**
     * 为data设置json数组 （多个）
     *
     * @param pojosMap the map
     */
    public void setDataArrays(Map<String, Collection<?>> pojosMap) {
        requireDataNotNull();
        setArraysForObject(data, pojosMap);
    }

    /**
     * 为data一个数组，其中数组是由一个集合转换而来的，而数组里面的元素是由map转换而来的
     *
     * @param key   键
     * @param value 值（集合）
     */
    public void setDataMapArray(String key, Collection<Map<?, ?>> value) {
        JSONArray array = new JSONArray();
        for (Map<?, ?> map : value) {
            array.add(JSONObject.fromObject(map));
        }
        setDataJsonArray(key, array);
    }

    /**
     * 把pojo对象转换为json对象
     *
     * @param pojo the pojo
     * @return json 对象
     */
    public static JSONObject parseFromObject(Object pojo) {
        JSONObject temp = new JSONObject();
        Class cls = pojo.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                temp.put(field.getName(), field.get(pojo));
            } catch (IllegalAccessException e) {
                temp.put(field.getName(), null);
            }
        }
        return temp;
    }

    /**
     * 把集合对象转换为json数组
     *
     * @param pojos pojo对象的集合
     * @return json 数组
     */
    public static JSONArray parseFromCollection(Collection<?> pojos) {
        JSONArray temp = new JSONArray();
        for (Object pojo : pojos) {
            temp.add(parseFromObject(pojo));
        }
        return temp;
    }

    /**
     * 为json对象增加json数组
     * obj : {
     * key: [
     * {...},
     * {...},
     * ...
     * ]
     * }
     *
     * @param jsonObject the Json Object
     * @param key        the key
     * @param pojos      pojo对象的集合
     */
    public static void setArrayForObject(JSONObject jsonObject, String key, Collection<?> pojos) {
        jsonObject.put(key, parseFromCollection(pojos));
    }

    /**
     * 为json对象增加多个json数组
     * obj: {
     * key1: [
     * {...},
     * {...},
     * ...
     * ],
     * key2: [
     * {...},
     * {...},
     * ...
     * ],
     * ...
     * }
     *
     * @param jsonObject the Json Object
     * @param map        the map 值为pojo对象的map
     */
    public static void setArraysForObject(JSONObject jsonObject, Map<String, Collection<?>> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            jsonObject.put(key, parseFromCollection(map.get(key)));
        }
    }

    /**
     * 为json对象增加json对象
     * obj: {
     * class's key: {
     * ... pojo pojo's field's key and value
     * }
     * }
     *
     * @param jsonObject the Json Object
     * @param pojo       the pojo
     */
    public static void setObjectForObject(JSONObject jsonObject, Object pojo) {
        jsonObject.put(pojo.getClass().getSimpleName(), parseFromObject(pojo));
    }

    /**
     * 为json对象增加json对象
     * obj: {
     * key: {
     * ... pojo pojo's field's key and value
     * }
     * }
     *
     * @param jsonObject the Json Object
     * @param key        the key
     * @param pojo       the pojo
     */
    public static void setObjectForObject(JSONObject jsonObject, String key, Object pojo) {
        jsonObject.put(key, parseFromObject(pojo));
    }

    /**
     * 为json对象增加多个json对象
     * obj: {
     * key1: {
     * ... pojo pojo's field's key and value
     * },
     * key2: {
     * ... pojo pojo's field's key and value
     * },
     * ...
     * }
     *
     * @param jsonObject the Json Object
     * @param map        the map
     */
    public static void setObjectsForObject(JSONObject jsonObject, Map<String, ?> map) {
        Iterator entries = map.entrySet().iterator();
        Map.Entry entry;
        String key;
        Object val;
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            key = (String) entry.getKey();
            val = entry.getValue();
            jsonObject.put(key, parseFromObject(val));
        }
    }

    /**
     * 为json对象增加多个json对象
     * obj: {
     * class1's key: {
     * ... pojo pojo's field's key and value
     * },
     * class2's key: {
     * ... pojo pojo's field's key and value
     * },
     * ...
     * }
     *
     * @param jsonObject the Json Object
     * @param pojos      pojo对象集合
     */
    public static void setObjectsForObject(JSONObject jsonObject, Collection<?> pojos) {
        for (Object o : pojos) {
            jsonObject.put(o.getClass().getSimpleName().toLowerCase(), parseFromObject(o));
        }
    }

    /**
     * 为json数组增加json对象（由pojo对象转换过来的）
     * array: [
     * {
     * ... pojo pojo's field's key and value
     * }
     * ]
     *
     * @param jsonArray the array
     * @param pojos     pojo对象集合
     */
    public static void setObjectForArray(JSONArray jsonArray, Collection<?> pojos) {
        jsonArray.add(parseFromObject(pojos));
    }

    /**
     * 为json数组增加多个json对象（由pojo对象转换过来的）
     * array: [
     * ... old value
     * {
     * ... new pojo pojo's field's key and value
     * },
     * {
     * ... new pojo pojo's field's key and value
     * },
     * ...
     * ]
     *
     * @param jsonArray the array
     * @param pojos     Pojo对象的集合
     */
    public static void setObjectsForArray(JSONArray jsonArray, Collection<?> pojos) {
        jsonArray.addAll(parseFromCollection(pojos));
    }

    private boolean dataEmpty() {
        return data.equals(EMPTY_JSON_OBJECT);
    }

    private void initData() {
        data = new JSONObject();
    }

    private void requireDataNotNull() {
        if (dataEmpty()) {
            initData();
        }
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
