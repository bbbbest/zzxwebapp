package cn.zzx.service;

import cn.zzx.bean.AdminRole;

import java.util.List;
import java.util.Optional;

/**
 * The interface Admin role service.
 *
 * @author fzh
 * @since 2017 /10/22
 */
public interface AdminRoleService {

    /**
     * 根据权限ID查找权限
     *
     * @param id the id
     * @return the optional
     */
    Optional<AdminRole> findByAdminRoleId(Integer id);

    /**
     * 查找所有权限
     *
     * @return the list
     */
    List<AdminRole> findAll();

    /**
     * 更新权限
     *
     * @param role the role
     */
    void update(AdminRole role);
}
