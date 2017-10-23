package cn.zzx.service;

import cn.zzx.bean.Bicycle;

import java.util.List;
import java.util.Optional;

/**
 * The interface Bicycle service.
 *
 * @author fzh
 * @since 2017 /10/23
 */
public interface BicycleService {
    /**
     * 抓取全部（数目）
     *
     * @return the integer
     */
    Integer findTotal();

    /**
     * 查找一页
     *
     * @param start    起始页
     * @param pageSize 页大小
     * @return 结果集 list
     */
    List<Bicycle> findByPage(int start, int pageSize);

    /**
     * 根据ID查找
     *
     * @param id 自行车ID
     * @return the optional
     */
    Optional<Bicycle> findById(Integer id);

    /**
     * 根据锁ID查找
     *
     * @param lockId 锁ID
     * @return the optional
     */
    Optional<Bicycle> findByLockId(Integer lockId);

    /**
     * 查找复合输入前缀的自行车编号
     *
     * @param prefix 前缀
     * @return 结果集 list
     */
    List<Integer> findIdByPrefix(String prefix);

    /**
     * 查找复合输入前缀的自行车锁编号
     *
     * @param prefix 前缀
     * @return 结果集 list
     */
    List<Integer> findLockIdByPrefix(String prefix);

    /**
     * 更新自行车的锁编号以及状态
     *
     * @param id     自行车ID
     * @param lockId 锁ID
     * @param status 自行车状态
     * @return 修改结果 boolean
     */
    boolean update(Integer id, Integer lockId, Byte status);

    /**
     * 根据ID删除一个自行车
     *
     * @param id 自行车ID
     * @return 修改结果 boolean
     */
    boolean delete(Integer id);

    /**
     * 未注册的用户贡献车子，需要为其注册账户，并将车子添加进去
     *
     * @param username the username
     * @param lockId   the lock id
     * @return the boolean
     */
    boolean saveOnRegistered(String username, Integer lockId);

    /**
     * 已注册的用户贡献车子
     *
     * @param name   the name
     * @param phone  the phone
     * @param lockId the lock id
     * @return the boolean
     */
    boolean saveOnUnregistered(String name, String phone, Integer lockId);

    /**
     * 公司批量订购
     *
     * @param objects the objects
     * @return the boolean
     */
    boolean saveOnCompany(Object[] objects);
}
