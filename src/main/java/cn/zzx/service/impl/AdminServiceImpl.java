package cn.zzx.service.impl;

import cn.zzx.bean.Admin;
import cn.zzx.dao.AdminDao;
import cn.zzx.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author fzh
 * @since 2017/10/22
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Resource(name = "adminDao")
    private AdminDao adminDao;

    @Override
    public Optional<Admin> findByUsernameAndPassword(String username, String password) {
        Admin admin = adminDao.selectByUsernameAndPassword(username, password);
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.selectAll();
    }

    @Override
    public boolean save(String username, String password, String name, Integer roleId) {
        Admin admin = Admin.builder().username(username).password(password).name(name).adminRoleId(roleId).build();
        return admin.canInsert() && adminDao.insert(admin) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        Admin admin = Admin.builder().adminId(id).build();
        return admin.canDelete() && adminDao.delete(admin) > 0;
    }

    @Override
    public boolean update(Integer adminId, String name, Integer roleId) {
        Admin admin = Admin.builder().adminId(adminId).name(name).adminRoleId(roleId).build();
        return admin.canUpdate() && adminDao.update(admin) > 1;
    }

    @Override
    public boolean update(Integer adminId, String password) {
        Admin admin = Admin.builder().adminId(adminId).password(password).build();
        return admin.canUpdate() && adminDao.update(admin) > 0;
    }
}
