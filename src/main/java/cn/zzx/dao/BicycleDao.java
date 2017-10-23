package cn.zzx.dao;

import cn.zzx.bean.Bicycle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Bicycle dao.
 * @author fzh
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
     * Delete int.
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
     * Select bicycle.
     *
     * @param bicycle the bicycle
     * @return the bicycle
     */
    Bicycle select(Bicycle bicycle);

    /**
     * Select id by prefix list.
     *
     * @param prefix the prefix
     * @return the list
     */
    List<Integer> selectIdByPrefix(@Param("prefix") String prefix);

    /**
     * Select lock id by prefix list.
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
