package cn.zzx.dao;

import cn.zzx.bean.Advice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Advice dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("adviceDao")
public interface AdviceDao {
    /**
     * Insert int.
     *
     * @param advice the advice
     * @return the int
     */
    int insert(Advice advice);

    /**
     * Delete int.
     *
     * @param advice the advice
     * @return the int
     */
    int delete(Advice advice);

    /**
     * Update int.
     *
     * @param advice the advice
     * @return the int
     */
    int update(Advice advice);

    /**
     * Select advice.
     *
     * @param advice the advice
     * @return the advice
     */
    Advice select(Advice advice);

    /**
     * Select by page list.
     *
     * @param start    the start
     * @param pageSize the page size
     * @return the list
     */
    List<Advice> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
