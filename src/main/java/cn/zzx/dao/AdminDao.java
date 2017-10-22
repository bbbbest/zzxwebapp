package cn.zzx.dao;

import cn.zzx.bean.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Admin dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("adminDao")
public interface AdminDao {
    /**
     * 根据用户名-密码查找管理员
     *
     * @param username 用户名
     * @param password 密码
     * @return 管理员信息
     */
    Admin selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 查找所有管理员
     *
     * @return the list
     */
    List<Admin> selectAll();

    /**
     * 查找一个管理员
     *
     * @param admin 管理员信息（此处仅使用到id）
     * @return 管理员信息
     */
    Admin select(Admin admin);

    /**
     * 删除一个管理员
     *
     * @param admin 管理员信息
     * @return 更新条数
     */
    int delete(Admin admin);

    /**
     * 新增一个管理员
     *
     * @param admin 管理员信息
     * @return 更新条数
     */
    int insert(Admin admin);

    /**
     * 更新管理员信息
     *
     * @param admin 管理员信息
     * @return 更新条数
     */
    int update(Admin admin);

    /**
     * 查询当前总记录数
     *
     * @return the integer
     */
    Integer selectTotal();
}
