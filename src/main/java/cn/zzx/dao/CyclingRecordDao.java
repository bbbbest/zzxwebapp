package cn.zzx.dao;

import cn.zzx.bean.CyclingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Cycling record dao.
 *
 * @author fzh
 */
@Repository("cyclingRecordDao")
public interface CyclingRecordDao {

    /**
     * Delete by id int.
     *
     * @param cyclingRecord the cycling record
     * @return the int
     */
    int delete(CyclingRecord cyclingRecord);

    /**
     * Select by primary KEY cycling record.
     *
     * @param cyclingRecord the cycling record
     * @return the cycling record
     */
    CyclingRecord select(CyclingRecord cyclingRecord);

    /**
     * Select by page list.
     *
     * @param start    the start
     * @param pageSize the pagesize
     * @return the list
     */
    List<CyclingRecord> selectByPage(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * Select total int.
     *
     * @return the int
     */
    int selectTotal();
}