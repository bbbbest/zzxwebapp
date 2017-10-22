package cn.zzx.web.admin.controller;

import cn.zzx.bean.Admin;
import cn.zzx.service.AdminService;
import cn.zzx.util.JsonWebTokenUtil;
import cn.zzx.util.JsonWrapper;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author fzh
 * @since 2017/10/22
 */
@RestController
public class AdminController {

    @Resource(name = "adminService")
    private AdminService adminService;

    @PostMapping(value = "login")
    public JSONObject login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Admin> ao = adminService.findByUsernameAndPassword(username, password);
        if (ao.isPresent()) {
            Admin admin = ao.get();
            return JsonWrapper.builder()
                    .setStatus(200)
                    .setMsg("success")
                    .setDataEntry("adminId", admin.getAdminId())
                    .setDataEntry("username", admin.getUsername())
                    .setDataEntry("name", admin.getName())
                    .setDataEntry("privilegeId", admin.getAdminRoleId())
                    .setDataEntry("jwt", JsonWebTokenUtil.generateJWT(admin.getUsername()))
                    .build();
        } else {
            return JsonWrapper.builder()
                    .setStatus(404)
                    .setMsg("not found")
                    .build();
        }
    }
}
