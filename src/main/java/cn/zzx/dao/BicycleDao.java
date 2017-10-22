package cn.zzx.dao;

import cn.zzx.bean.Bicycle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自行车的 dao 层操作，主要包括常用的增删改查
 * @author fzh & lj
 * @version 1.0
 */
@Repository("bicycleDao")
public interface BicycleDao {
    /**
     * Insert int.
     *
     * @param bicycle the bicycle
     * @return the int
     */
    int insert(Bicycle bicycle);

    /**
     * Delete by id int.
     *
     * @param bicycle the bicycle
     * @return the int
     */
    int delete(Bicycle bicycle);

    /**
     * Update by id selective int.
     *
     * @param bicycle the bicycle
     * @return the int
     */
    int updateByIdSelective(Bicycle bicycle);

    /**
     * Select by page list.
     *
     * @param start    the start
     * @param pageSize the page size
     * @return the list
     */
    List<Bicycle> selectByPage(@Param("start") int start, @Param("pageSize") int pageSize);


    /**
     * Select by id bicycle.
     *
     * @param bicycle the bicycle
     * @return the bicycle
     */
    Bicycle select(Bicycle bicycle);

    /**
     * Select by lock id bicycle.
     *
     * @param lockId the lock id
     * @return the bicycle
     */
    Bicycle selectByLockId(@Param("lockId") int lockId);

    /**
     * Select by id prefix list.
     *
     * @param prefix the prefix
     * @return the list
     */
    List<Integer> selectIdByPrefix(@Param("prefix") String prefix);

    /**
     * Select by lock id prefix list.
     *
     * @param prefix the prefix
     * @return the list
     */
    List<Integer> selectLockIdByPrefix(@Param("prefix") String prefix);

    /**
     * Select total int.
     *
     * @return the int
     */
    int selectTotal();
}
