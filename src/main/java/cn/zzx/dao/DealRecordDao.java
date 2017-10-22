package cn.zzx.dao;

import cn.zzx.bean.DealRecord;
import cn.zzx.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Deal record dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("dealRecordDao")
public interface DealRecordDao {
    /**
     * Delete int.
     *
     * @param dealRecord the deal record
     * @return the int
     */
    int delete(DealRecord dealRecord);

    /**
     * Insert int.
     *
     * @param dealRecord the deal record
     * @return the int
     */
    int insert(DealRecord dealRecord);

    /**
     * Update status.
     *
     * @param dealRecord the deal record
     */
    void updateStatus(DealRecord dealRecord);

    /**
     * Select deal record.
     *
     * @param dealRecord the deal record
     * @return the deal record
     */
    DealRecord select(DealRecord dealRecord);

    /**
     * Select by user and page list.
     *
     * @param user     the user
     * @param start    the start
     * @param pageSize the page size
     * @return the list
     */
    List<DealRecord> selectByUserAndPage(User user, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
