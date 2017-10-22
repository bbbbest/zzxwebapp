import cn.zzx.util.JsonWebTokenUtil;
import cn.zzx.util.JsonWrapper;
import cn.zzx.util.JsonWrapperBuilder;
import io.jsonwebtoken.impl.crypto.MacProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @author fzh
 * @since 2017/8/28
 */
@RestController
@ResponseBody
public class DemoTest {
    private static Key key = MacProvider.generateKey();
    private static String subject = "SBTEST";

    // login
    @PostMapping(value = "/login")
    public JSONObject adminTestPost(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        // 判断是否符合要求
        JsonWrapper result = new JsonWrapper();
        result.setStatus(200);
        result.setMsg("success");
        if (username.equals(password)) {
            JsonWrapper info = new JsonWrapper();
            info.setEntry("adminId", 1);
            info.setEntry("username", username);
            info.setEntry("name", "Tom");
            info.setEntry("privilegeId", 1);
            info.setEntry("jwt", JsonWebTokenUtil.generateJWT(username));
            info.setParent("data", result);
        } else {
            result.setStatus(404);
            result.setMsg("not found");
        }
        return result.get();
    }

    // bikes dispatch method
    @GetMapping(value = "/bikes")
    public JSONObject bikes(HttpServletRequest request, HttpServletResponse response) {
        if (allNotNull(request.getParameter("start"), request.getParameter("pagesize"))) {
            int start = Integer.parseInt(request.getParameter("start"));
            int pagesize = Integer.parseInt(request.getParameter("pagesize"));
            return bikesPagination(start, pagesize);
        } else if (allNotNull(request.getParameter("type"), request.getParameter("value"))) {
            return bikesQuery(request.getParameter("type"), request.getParameter("value"));
        } else if (allNotNull(request.getParameter("type"), request.getParameter("prefix"))) {
            return bikeDynamicQuery(request.getParameter("type"), request.getParameter("prefix"));
        } else {
            return bikeTotal();
        }
    }

    // total
    private JSONObject bikeTotal() {
        return total(200);
    }

    // pagination
    private JSONObject bikesPagination(int start, int pagesize) {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonArray("values", randomBikeArray((start - 1) * pagesize, pagesize))
                .build();
    }

    // query
    private JSONObject bikesQuery(String type, String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JsonWrapper val = new JsonWrapper();
        val.setEntry("from", 1 + Integer.parseInt(value));
        val.setEntry("time", sdf.format(new Date()));
        val.setEntry("photo", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=1ee1e351bf014a9095334eefc81e5277/a044ad345982b2b7d054652737adcbef77099b0d.jpg");
        JsonWrapper location = new JsonWrapper();
        location.setEntry("x", Math.random() * 100);
        location.setEntry("y", Math.random() * 100);
        location.setParent("location", val);
        val.setEntry("status", 1);
        val.setEntry("energy", 100);
        if ("id".equals(type)) {
            val.setEntry("id", Integer.parseInt(value));
            val.setEntry("lockId", (Integer.parseInt(value) + 1) << 10);
        } else if ("lockId".equals(type)) {
            val.setEntry("id", (Integer.parseInt(value) + 1) << 10);
            val.setEntry("lockId", Integer.parseInt(value));
        }
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", val.get())
                .build();
    }

    // dynamic query
    private JSONObject bikeDynamicQuery(String type, String prefix) {
        return dynamicQuery(prefix);
    }

    @PostMapping(value = "/bikes")
    public JSONObject bikeAdd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = JSONObject.fromObject(readJSONString(request));
        int role = (Integer) data.get("role");
        String username = null;
        String lockId = null;
        JSONArray values = null;
        if (role == 1 || role == -1) {
            JSONObject value = data.getJSONObject("value");
            username = value.getString("username");
            lockId = value.getString("lockId");
        } else {
            values = data.getJSONArray("values");
        }
        JsonWrapperBuilder builder = new JsonWrapperBuilder().setStatus(200).setMsg("success");
        return allNotNull(username, lockId) ?
                builder.setDataEntry("username", username).setDataEntry("lockId", lockId).build()
                : builder.setDataJsonArray("values", values).build();
    }

    // modify
    @PutMapping(value = "/bikes")
    public JSONObject bikeModify(@RequestBody Map map) {
        System.out.println("bikes modifying...");
        JSONObject json = JSONObject.fromObject(map);
        Number id = (Number) json.get("id");
        Number lockId = (Number) json.get("lockId");
        Number status = (Number) json.get("status");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JsonWrapper value = new JsonWrapper();
        value.setEntry("id", id);
        value.setEntry("masterId", 1);
        value.setEntry("time", sdf.format(new Date()));
        value.setEntry("photo", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=1ee1e351bf014a9095334eefc81e5277/a044ad345982b2b7d054652737adcbef77099b0d.jpg");
        value.setEntry("lockId", lockId);
        JsonWrapper location = new JsonWrapper();
        location.setEntry("x", Math.random() * 100);
        location.setEntry("y", Math.random() * 100);
        location.setParent("location", value);
        value.setEntry("status", status);
        value.setEntry("energy", 100);
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", value.get())
                .build();
    }

    // DELETE
    @DeleteMapping(value = "/bikes")
    public JSONObject bikeDelete(@RequestParam("id") String id) {
        System.out.println(id);
        return successEmptyJSON();
    }

    // users
    // dispatch method
    @GetMapping(value = "/users")
    public JSONObject users(HttpServletRequest request, HttpServletResponse response) {
        if (allNotNull(request.getParameter("type"), request.getParameter("value"))) {
            return usersQuery(request.getParameter("type"), request.getParameter("value"));
        } else if (allNotNull(request.getParameter("type"), request.getParameter("prefix"))) {
            return userDynamicQuery(request.getParameter("type"), request.getParameter("prefix"));
        } else if (allNotNull(request.getParameter("t"))) {
            String t = request.getParameter("t");
            if ("havemsg".equals(t)) {
                return userUnProTotal();
            } else {
                return new JsonWrapperBuilder().setStatus(200).setDataJsonArray("values", new JSONArray()).build();
            }
        } else if (allNotNull(request.getParameter("start"), request.getParameter("pagesize"))) {
            int start = Integer.parseInt(request.getParameter("start"));
            int pagesize = Integer.parseInt(request.getParameter("pagesize"));
            return usersPagination(start, pagesize);
        } else {
            return userTotal();
        }
    }

    // total
    private JSONObject userTotal() {
        return total(200);
    }

    private JSONObject userUnProTotal() {
        return total(1);
    }

    // dynamic query
    private JSONObject userDynamicQuery(String type, String prefix) {
        return dynamicQuery(prefix);
    }

    // query
    private JSONObject usersQuery(String type, String val) {
        JsonWrapper value = new JsonWrapper();
        value.setEntry("userId", val);
        value.setEntry("username", randomString());
        value.setEntry("name", randomString("特定用户", 6, null));
        value.setEntry("cardNumber", randomNumberString());
        value.setEntry("score", 100);
        value.setEntry("phone", "158" + randomNumberString(8));
        value.setEntry("status", 1);
        value.setEntry("balance", 20);
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", value.get()).build();
    }

    // pagination
    private JSONObject usersPagination(int start, int pagesize) {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonArray("values", randomUserArray((start - 1) * pagesize, pagesize))
                .build();
    }

    // modify
    @PutMapping(value = "/users")
    public JSONObject userModify() {
        return successEmptyJSON();
    }

    // modify
    @DeleteMapping(value = "/users")
    public JSONObject userDelete() {
        return successEmptyJSON();
    }

    // add
    @PostMapping(value = "/users")
    public JSONObject userAdd(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = JSONObject.fromObject(readJSONString(request));
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", data)
                .build();
    }

    // feedback
    @GetMapping(value = "/feedback")
    public JSONObject feedback(HttpServletRequest request, HttpServletResponse response) {
        if (allNotNull(request.getParameter("type"), request.getParameter("value"))) {
            return feedbackQuery(request.getParameter("type"), request.getParameter("value"));
        } else if (allNotNull(request.getParameter("type"), request.getParameter("prefix"))) {
            return feedbackDynamicQuery(request.getParameter("type"), request.getParameter("prefix"));
        } else if (allNotNull(request.getParameter("t"))) {
            String t = request.getParameter("t");
            if ("havemsg".equals(t)) {
                return userUnProTotal();
            } else {
                return new JsonWrapperBuilder().setStatus(200).setDataJsonArray("values", new JSONArray()).build();
            }
        } else if (allNotNull(request.getParameter("start"), request.getParameter("pagesize"))) {
            int start = Integer.parseInt(request.getParameter("start"));
            int pagesize = Integer.parseInt(request.getParameter("pagesize"));
            return feedbackPagination(start, pagesize);
        } else {
            return feedbackTotal();
        }
    }

    private JSONObject feedbackTotal() {
        return total(200);
    }

    private JSONObject feedbackPagination(int start, int pagesize) {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonArray("values", randomFeedbackArray((start - 1) * pagesize, pagesize)).build();
    }

    private JSONObject feedbackDynamicQuery(String type, String prefix) {
        return dynamicQuery(prefix);
    }

    private JSONObject feedbackQuery(String type, String val) {
        int code = randomNumber(4);
        LocalDate now = LocalDate.now();
        JsonWrapper value = new JsonWrapper();
        value.setEntry("id", val);
        value.setEntry("creator", randomNumber(4));
        value.setEntry("title", "special" + randomString(6));
        value.setEntry("content", Content.askContents[code % 3]);
        value.setEntry("createTime", now.toString());
        value.setEntry("status", code % 2);
        value.setEntry("replyContent", code % 2 == 0 ? "" : Content.replyContents[code % 3]);
        value.setEntry("replyTime", code % 2 == 0 ? "" : now.toString());
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", value.get()).build();
    }

    @PutMapping("/feedback")
    public JSONObject feedbackModify(@RequestBody Map map) {
        System.out.println(map.get("id"));
        System.out.println(map.get("replyContent"));
        return successEmptyJSON();
    }

    @DeleteMapping("/feedback")
    public JSONObject feedbackDelete(int id) {
        return successEmptyJSON();
    }

    // activities
    @GetMapping(value = "/activities")
    public JSONObject activities(HttpServletRequest request, HttpServletResponse response) {
        if (allNotNull(request.getParameter("type"), request.getParameter("value"))) {
            return activitiesQuery(request.getParameter("type"), request.getParameter("value"));
        } else if (allNotNull(request.getParameter("type"), request.getParameter("prefix"))) {
            return activitiesDynamicQuery(request.getParameter("type"), request.getParameter("prefix"));
        } else if (allNotNull(request.getParameter("t"))) {
            String t = request.getParameter("t");
            if ("havemsg".equals(t)) {
                return userUnProTotal();
            } else {
                return new JsonWrapperBuilder().setStatus(200).setDataJsonArray("values", new JSONArray()).build();
            }
        } else if (allNotNull(request.getParameter("start"), request.getParameter("pagesize"))) {
            int start = Integer.parseInt(request.getParameter("start"));
            int pagesize = Integer.parseInt(request.getParameter("pagesize"));
            return activitiesPagination(start, pagesize);
        } else {
            return activitiesTotal();
        }
    }

    private JSONObject activitiesTotal() {
        return total(200);
    }

    private JSONObject activitiesDynamicQuery(String type, String prefix) {
        return dynamicQuery(prefix);
    }

    private JSONObject activitiesQuery(String type, String val) {
        int code = randomNumber(4);
        JsonWrapper result = new JsonWrapper();
        LocalDate now = LocalDate.now();
        result.setEntry("id", val);
        result.setEntry("userId", randomNumber(4));
        result.setEntry("title", "特定活动-" + randomNumberString(6));
        result.setEntry("createTime", now.toString());
        result.setEntry("startTime", now.plusDays(5).toString());
        result.setEntry("endTime", now.plusDays(10).toString());
        result.setEntry("description", Content.activitiesDescription[code % 3]);
        result.setEntry("status", code % 2);
        result.setEntry("replyContent", code % 2 == 0 ? "" : Content.activitiesReply[code % 3]);
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("value", result.get()).build();
    }

    private JSONObject activitiesPagination(int start, int pagesize) {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonArray("values", randomActivities((start - 1) * pagesize, pagesize)).build();
    }

    @PutMapping("/activities")
    public JSONObject activitiesModify(@RequestBody Map map) {
        System.out.println(map.get("id"));
        System.out.println(map.get("status"));
        return successEmptyJSON();
    }

    @PostMapping("/activities")
    public JSONObject createActivity(@RequestBody Map map) {
        System.out.println(map);
        return successEmptyJSON();
    }

    // me
    // 测试修改密码
    @RequestMapping(value = "/pwd", method = RequestMethod.POST)
    public JSONObject adminPWDReset(@RequestParam("old") String old, @RequestParam("n") String n) {
        JSONObject jsonObject;
        if (old.equals(n)) {
            jsonObject = failedJSON();
        } else {
            jsonObject = successEmptyJSON();
        }
        return jsonObject;
    }

    // privileges
    @GetMapping("/privileges")
    public JSONObject privilege(HttpServletRequest request, HttpServletResponse response) {
        String t = request.getParameter("t");
        Objects.requireNonNull(t);
        switch (t) {
            case "total":
                return privilegesTotal();
            case "all":
                return privilegesAll();
            case "admin":
                return privilegesAllAdmin();
            case "id":
                return privilegesById(Integer.parseInt(request.getParameter("id")));
            default:
                return successEmptyJSON();
        }
    }

    private JSONObject privilegesTotal() {
        return total(20);
    }

    private JSONObject privilegesAll() {
        String[] args = {"admin", "user", "activity", "advice", "bicycle", "cyclingrecord", "dealrecord"};
        JsonWrapper result = new JsonWrapper();
        JSONArray values = new JSONArray();
        for (int i = 0; i < 5; i++) {
            JsonWrapper value = new JsonWrapper();
            for (int j = 0; j < args.length; j++) {
                JsonWrapper item = new JsonWrapper();
                boolean f = (j * i) % 2 == 1;
                item.setEntry("query", f);
                item.setEntry("update", !f);
                item.setParent(args[j], value);
            }
            value.setEntry("id", i + 1);
            values.add(value.get());
        }
        result.setStatus(200);
        result.setMsg("success");
        result.setDataJsonArray("values", values);
        return result.get();
    }

    private JSONObject privilegesById(Integer id) {
        String[] args = {"admin", "user", "activity", "advice", "bicycle", "cyclingrecord", "dealrecord"};
        int i = id;
        JSONObject val = new JSONObject();
        for (String arg : args) {
            JsonWrapper item = new JsonWrapper();
            item.setEntry("query", true);
            item.setEntry("update", true);
            item.setParent(arg, val);
        }
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonObject("privilege", val).build();
    }

    private JSONObject privilegesAllAdmin() {
        String[] args = {"admin", "user", "activity", "advice", "bicycle", "cyclingrecord", "dealrecord"};
        JSONArray admins = new JSONArray();
        for (int i = 0; i < 20; i++) {
            JsonWrapper admin = new JsonWrapper();
            JsonWrapper auth = new JsonWrapper();
            for (int j = 0; j < args.length; j++) {
                JsonWrapper item = new JsonWrapper();
                boolean f = j % 2 == 1;
                item.setEntry("query", f);
                item.setEntry("update", !f);
                item.setParent(args[j], auth);
            }
            admin.setEntry("adminId", (i + 1));
            admin.setEntry("name", "Jane" + (i + 1));
            admin.setEntry("roleId", 1);
            auth.setParent("auth", admin);
            admins.add(admin.get());
        }
        return new JsonWrapperBuilder().setStatus(200)
                .setMsg("success").setDataJsonArray("admins", admins).build();
    }

    private boolean allNotNull(Object... objects) {
        boolean result = true;
        for (Object object : objects) {
            result = result && (object != null);
        }
        return result;
    }

    private boolean anyNotNull(Object... objects) {
        boolean result = false;
        for (Object object : objects) {
            result = result || (object != null);
        }
        return result;
    }

    private String readJSONString(HttpServletRequest request) {
        StringBuilder json = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
    }

    private static Random random = new Random();

    private String randomString(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int code = random.nextInt(26);
            builder.append((char) (code % 2 == 1 ? code + 65 : code + 97));
        }
        return builder.toString();
    }

    private String randomString(String prefix, int len, String suffix) {
        StringBuilder builder = new StringBuilder(prefix);
        for (int i = 0; i < len; i++) {
            int code = random.nextInt(26);
            builder.append((char) (code % 2 == 1 ? code + 65 : code + 97));
        }
        if (allNotNull(suffix)) {
            builder.append(suffix);
        }
        return builder.toString();
    }

    private String randomString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int code = random.nextInt(26);
            builder.append((char) (code % 2 == 1 ? code + 65 : code + 97));
        }
        return builder.toString();
    }

    /**
     * 默认为 6 位长度的随机数字
     *
     * @return
     */
    private int randomNumber() {
        int result = 0;
        for (int i = 0; i < 6; i++) {
            result *= 10;
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * len位数的随机数字
     *
     * @param len
     * @return
     */
    private int randomNumber(int len) {
        int result = 0;
        for (int i = 0; i < len; i++) {
            result *= 10;
            result += random.nextInt(10);
        }
        return result;
    }

    private String randomNumberString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int code = random.nextInt(10) + 48;
            builder.append((char) code);
        }
        return builder.toString();
    }

    private String randomNumberString(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int code = random.nextInt(10) + 48;
            builder.append((char) code);
        }
        return builder.toString();
    }

    private JSONObject randomBike(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JsonWrapper value = new JsonWrapper();
        value.setEntry("id", i + 1);
        value.setEntry("from", i + 1);
        value.setEntry("name", "用户名");
        value.setEntry("time", sdf.format(new Date()));
        value.setEntry("photo", "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=1ee1e351bf014a9095334eefc81e5277/a044ad345982b2b7d054652737adcbef77099b0d.jpg");
        value.setEntry("lockId", (i + 1) << 10);
        JsonWrapper location = new JsonWrapper();
        location.setEntry("x", Math.random() * 100);
        location.setEntry("y", Math.random() * 100);
        location.setParent("location", value);
        value.setEntry("status", 1);
        value.setEntry("energy", 100);
        return value.get();
    }

    private JSONArray randomBikeArray(int idStart, int size) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < size; i++) {
            array.add(randomBike(idStart + i));
        }
        return array;
    }

    private JSONObject randomUser(int id) {
        JsonWrapper value = new JsonWrapper();
        value.setEntry("userId", id + 1);
        value.setEntry("username", randomString());
        value.setEntry("name", randomString("用户", 6, null));
        value.setEntry("cardNumber", randomNumberString());
        value.setEntry("score", 60);
        value.setEntry("phone", "155" + randomNumberString(8));
        value.setEntry("status", 1);
        value.setEntry("balance", 100);
        return value.get();
    }

    private JSONArray randomUserArray(int idStart, int size) {
        JSONArray values = new JSONArray();
        for (int i = 0; i < size; i++) {
            values.add(randomUser(idStart + i));
        }
        return values;
    }

    private JSONObject randomFeedback(int id) {
        JsonWrapper result = new JsonWrapper();
        LocalDate now = LocalDate.now();
        result.setEntry("id", id + 1);
        result.setEntry("creator", randomNumber(4));
        result.setEntry("title", "反馈-" + randomNumberString(6));
        result.setEntry("content", Content.askContents[id % 3]);
        result.setEntry("createTime", now.toString());
        result.setEntry("status", id % 2);
        result.setEntry("replyContent", id % 2 == 0 ? "" : Content.replyContents[id % 3]);
        result.setEntry("replyTime", id % 2 == 0 ? "" : now.toString());
        return result.get();
    }

    private JSONArray randomFeedbackArray(int idStart, int size) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < size; i++) {
            array.add(randomFeedback(idStart + i));
        }
        return array;
    }

    private JSONObject randomActivity(int id) {
        JsonWrapper result = new JsonWrapper();
        LocalDate now = LocalDate.now();
        result.setEntry("id", id + 1);
        result.setEntry("userId", randomNumber(4));
        result.setEntry("title", "活动-" + randomNumberString(6));
        result.setEntry("createTime", now.toString());
        result.setEntry("startTime", now.plusDays(5).toString());
        result.setEntry("endTime", now.plusDays(10).toString());
        result.setEntry("description", Content.activitiesDescription[id % 3]);
        result.setEntry("status", id % 2);
        result.setEntry("replyContent", id % 2 == 0 ? "" : Content.activitiesReply[id % 3]);
        return result.get();
    }

    private JSONArray randomActivities(int idStart, int size) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < size; i++) {
            array.add(randomActivity(idStart + i));
        }
        return array;
    }

    private JSONObject successEmptyJSON() {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success").build();
    }

    private JSONObject failedJSON() {
        return new JsonWrapperBuilder()
                .setStatus(304)
                .setMsg("failed").build();
    }

    private JSONObject notFound() {
        return new JsonWrapperBuilder()
                .setStatus(404)
                .setMsg("not found").build();
    }

    private JSONObject total(int total) {
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataEntry("total", total).build();
    }

    private static class Content {
        static String[] askContents = new String[]{
                "你们为什么做的这么好啊",
                "你们为什么这么便宜啊",
                "你们为什么服务的这么好啊"
        };

        static String[] replyContents = new String[]{
                "优质的服务是我们的追求",
                "大家好才是真的好",
                "因为良心！"
        };

        static String[] activitiesDescription = new String[]{
                "大家一块出来嗨一嗨，不要宅到屋子里啊！",
                "周末骑车去 α-5831 星云吧！",
                "来一次宇宙大逃亡如何？！"
        };

        static String[] activitiesReply = new String[]{
                "活动很好，批准！",
                "想法不错，支持！",
                "OK！"
        };
    }

    private JSONObject dynamicQuery(String prefix) {
        JSONArray values = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JsonWrapper value = new JsonWrapper();
            value.setEntry("value", prefix + (100 + i));
            value.setParent(values);
        }
        return new JsonWrapperBuilder()
                .setStatus(200)
                .setMsg("success")
                .setDataJsonArray("values", values)
                .build();
    }
}
