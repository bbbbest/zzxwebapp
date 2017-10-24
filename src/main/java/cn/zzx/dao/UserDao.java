package cn.zzx.dao;

import cn.zzx.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("userDao")
public interface UserDao {
    /**
     * 增加用户
     *
     * @param user 用户信息
     * @return 更新条数
     */
    int insert(User user);

    /**
     * 删除用户
     *
     * @param user 用户信息
     * @return 更新条数
     */
    int delete(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新条数
     */
    int update(User user);

    /**
     * 查询总共多少用户
     *
     * @return 用户数
     */
    Integer selectTotal();

    /**
     * 按页查询用户
     *
     * @param start    起始页
     * @param pageSize 页条目数
     * @return 用户列表
     */
    List<User> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    /**
     * 查找用户
     *
     * @param user 用户信息（只包含userId 或者 username）
     * @return 查到的用户信息
     */
    User select(User user);

    /**
     * 用户登录时使用
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户信息
     */
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
