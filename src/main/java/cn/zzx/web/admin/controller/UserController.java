package cn.zzx.web.admin.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fzh
 * @since 2017/10/23
 */
@RestController
@RequestMapping("users")
public class UserController {

    // TODO

    @GetMapping
    public JSONObject get(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(123);
        return null;
    }

    @PostMapping
    public JSONObject post(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(456);
        return null;
    }

    @PutMapping
    public JSONObject put(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @DeleteMapping
    public JSONObject delete(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
