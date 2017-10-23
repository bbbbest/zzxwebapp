package cn.zzx.service;

import cn.zzx.bean.Admin;

import java.util.List;
import java.util.Optional;

/**
 * The interface Admin service.
 *
 * @author fzh
 * @since 2017 /10/22
 */
public interface AdminService {
    /**
     * 根据用户名和密码查找管理员
     *
     * @param username 用户名
     * @param password 密码
     * @return optional optional
     */
    Optional<Admin> findByUsernameAndPassword(String username, String password);

    /**
     * 查找所有管理员
     *
     * @return 所有管理员 list
     */
    List<Admin> findAll();

    /**
     * 添加一个管理员
     *
     * @param username 用户名
     * @param password 密码
     * @param name     姓名
     * @param roleId   权限ID
     * @return 操作结果 boolean
     */
    boolean save(String username, String password, String name, Integer roleId);

    /**
     * 根据管理员ID删除管理员
     *
     * @param id the 管理员ID
     * @return 操作结果 boolean
     */
    boolean delete(Integer id);

    /**
     * 根据管理员ID更新管理员的姓名和权限
     *
     * @param adminId 管理员ID
     * @param name    管理员姓名
     * @param roleId  权限ID
     * @return 操作结果 boolean
     */
    boolean update(Integer adminId, String name, Integer roleId);

    /**
     * 更新密码
     *
     * @param adminId  管理员ID
     * @param password 新密码
     * @return 操作结果 boolean
     */
    boolean update(Integer adminId, String password);
}
