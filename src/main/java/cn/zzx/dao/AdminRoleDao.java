package cn.zzx.dao;

import cn.zzx.bean.AdminRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Admin role dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("adminRoleDao")
public interface AdminRoleDao {
    /**
     * Select all list.
     *
     * @return the list
     */
    List<AdminRole> selectAll();

    /**
     * Delete int.
     *
     * @param role the role
     * @return the int
     */
    int delete(AdminRole role);

    /**
     * Update int.
     *
     * @param role the role
     * @return the int
     */
    int update(AdminRole role);

    /**
     * Select admin role.
     *
     * @param role the role
     * @return the admin role
     */
    AdminRole select(AdminRole role);
}
