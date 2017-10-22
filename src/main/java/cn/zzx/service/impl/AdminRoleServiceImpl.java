package cn.zzx.service.impl;

import cn.zzx.bean.AdminRole;
import cn.zzx.dao.AdminRoleDao;
import cn.zzx.service.AdminRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author fzh
 * @since 2017/10/22
 */
@Service("adminRoleService")
public class AdminRoleServiceImpl implements AdminRoleService {

    @Resource(name = "adminRoleDao")
    private AdminRoleDao adminRoleDao;

    @Override
    public Optional<AdminRole> findByAdminRoleId(Integer id) {
        AdminRole r = AdminRole.builder().adminRoleId(id).build();
        if (r.canSelect()) {
            return Optional.ofNullable(adminRoleDao.select(r));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<AdminRole> findAll() {
        return adminRoleDao.selectAll();
    }

    @Override
    public void update(AdminRole role) {
        if (role.canUpdate()) {
            adminRoleDao.update(role);
        }
    }
}
