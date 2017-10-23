package cn.zzx.web.admin.controller;

import cn.zzx.bean.Bicycle;
import cn.zzx.service.BicycleService;
import cn.zzx.util.JsonWrapper;
import cn.zzx.util.ObjectUtil;
import cn.zzx.util.UnifiedResponse;
import net.sf.json.JSONObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.zzx.util.ObjectUtil.*;
import static cn.zzx.util.UnifiedResponse.invalidArguments;

/**
 * @author fzh
 * @since 2017/10/23
 */
@RestController
@RequestMapping("bikes")
public class BicycleController {
    @Resource(name = "bicycleService")
    private BicycleService service;

    // ========================================================================
    @GetMapping
    public JSONObject get(Integer start, Integer pagesize, String type, String value, String prefix) {
        if (allNonNull(start, pagesize)) {
            return doPagination(start, pagesize);
        } else if (leastOneNull(start, pagesize)) {
            return invalidArguments();
        } else if (nonNull(type)) {
            if (nonNull(value)) {
                return doTypeAndValue(type, value);
            }
            if (nonNull(prefix)) {
                return doTypeAndPrefix(type, prefix);
            }
            // 有 type 却没 value 或 prefix
            return invalidArguments();
        } else if (leastOneNonNull(value, prefix)) {
            // 有 value 或 prefix 却没 type
            return invalidArguments();
        } else {
            // 查看总记录数
            return doTotal();
        }
    }

    @Nullable
    @Contract(pure = true)
    private JSONObject doPagination(Integer start, Integer pagesize) {
        return JsonWrapper.builder()
                .setStatus(200)
                .setMsg("success")
                .setDataEntry("bikes", JsonWrapper.parseFromCollection(service.findByPage(start, pagesize)))
                .build();
    }

    @Nullable
    @Contract(pure = true)
    private JSONObject doTypeAndValue(String type, String value) {
        Optional<Bicycle> o;
        switch (type) {
            case "id":
                o = service.findById(Integer.valueOf(value));
                break;
            case "lockId":
                o = service.findByLockId(Integer.valueOf(value));
                break;
            default:
                return UnifiedResponse.invalidArguments();
        }
        if (o.isPresent()) {
            Bicycle bicycle = o.get();
            return JsonWrapper.builder().setStatus(200).setMsg("success")
                    .setDataJsonObject("value", JsonWrapper.parseFromObject(bicycle))
                    .build();
        }
        return JsonWrapper.builder().setStatus(201).setMsg("failed")
                .build();
    }

    @Nullable
    @Contract(pure = true)
    private JSONObject doTypeAndPrefix(String type, String prefix) {
        List<Integer> list;
        switch (type) {
            case "id":
                list = service.findIdByPrefix(prefix);
                break;
            case "lockId":
                list = service.findLockIdByPrefix(prefix);
                break;
            default:
                return UnifiedResponse.invalidArguments();
        }
        return JsonWrapper.builder().setStatus(200).setMsg("success").setDataJsonArray("values", JsonWrapper.parseFromListOfBaseType("value", list)).build();
    }

    @Nullable
    @Contract(pure = true)
    private JSONObject doTotal() {
        return JsonWrapper.builder()
                .setStatus(200)
                .setMsg("success")
                .setDataEntry("total", service.findTotal())
                .build();
    }

    // ========================================================================

    @PostMapping
    public JSONObject post(@RequestParam("role") Integer role, @RequestBody Map<String, Object> data) {
        // 1 registered     -1 unregistered     666 company
        JSONObject json = new JSONObject();
        json.putAll(data);
        boolean flag;
        switch (role) {
            case -1:
                flag = unRegistered(json);
                break;
            case 1:
                flag = registered(json);
                break;
            case 666:
                flag = company(json);
                break;
            default:
                return UnifiedResponse.invalidArguments();
        }
        return flag ?
                UnifiedResponse.successEmptyResp()
                : UnifiedResponse.failedEmptyResp();
    }

    @Contract(pure = true)
    private boolean registered(JSONObject data) {
        return service.saveOnRegistered(String.valueOf(data.get("username")), Integer.valueOf(String.valueOf(data.get("lockId"))));
    }

    @Contract(pure = true)
    private boolean unRegistered(JSONObject data) {
        return service.saveOnUnregistered(String.valueOf(data.get("name")), String.valueOf(data.get("phone")), Integer.valueOf(String.valueOf(data.get("lockId"))));
    }

    @Contract(pure = true)
    private boolean company(JSONObject data) {
        return service.saveOnCompany((Object[]) data.get("values"));
    }

    // ========================================================================

    @PutMapping
    public JSONObject put(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        Integer lockId = (Integer) map.get("lockId");
        Byte status = ((Integer) map.get("status")).byteValue();
        if (ObjectUtil.allNonNull(id, lockId, status)) {
            if (service.update(id, lockId, status)) {
                return JsonWrapper.builder().setStatus(200).setMsg("success").build();
            } else {
                return JsonWrapper.builder().setStatus(201).setMsg("failed").build();
            }
        } else {
            return UnifiedResponse.invalidArguments();
        }
    }

    // ========================================================================

    @DeleteMapping
    public JSONObject delete(@RequestParam("id") Integer id) {
        if (service.delete(id)) {
            return JsonWrapper.builder().setStatus(200).setMsg("success").build();
        } else {
            return JsonWrapper.builder().setStatus(201).setMsg("failed").build();
        }
    }
}
