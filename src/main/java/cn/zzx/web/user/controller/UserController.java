package cn.zzx.web.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fzh
 * @since 2017/10/22
 */
@RestController
public class UserController {
    @GetMapping("test")
    public String test() {
        return "user";
    }
}
