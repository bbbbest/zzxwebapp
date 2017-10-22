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
     * Insert int.
     *
     * @param user the user
     * @return the int
     */
    int insert(User user);

    /**
     * Delete int.
     *
     * @param user the user
     * @return the int
     */
    int delete(User user);

    /**
     * Update int.
     *
     * @param user the user
     * @return the int
     */
    int update(User user);

    /**
     * Select total integer.
     *
     * @return the integer
     */
    Integer selectTotal();

    /**
     * Select by page list.
     *
     * @param start    the start
     * @param pageSize the page size
     * @return the list
     */
    List<User> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
