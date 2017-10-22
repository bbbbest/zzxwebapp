package cn.zzx.dao;

import cn.zzx.bean.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Activity dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("activityDao")
public interface ActivityDao {
    /**
     * Delete int.
     *
     * @param activity the activity
     * @return the int
     */
    int delete(Activity activity);

    /**
     * Select by page list.
     *
     * @param start    the start
     * @param pageSize the page size
     * @return the list
     */
    List<Activity> selectByPage(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * Insert int.
     *
     * @param activity the activity
     * @return the int
     */
    int insert(Activity activity);

    /**
     * Update selective int.
     *
     * @param activity the activity
     * @return the int
     */
    int updateSelective(Activity activity);

    /**
     * Select selective list.
     *
     * @return the list
     */
    List<Activity> selectSelective();
}
