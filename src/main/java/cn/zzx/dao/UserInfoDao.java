package cn.zzx.dao;

import cn.zzx.bean.User;
import cn.zzx.bean.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * The interface User info dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("userInfoDao")
public interface UserInfoDao {
    /**
     * Select user info.
     *
     * @param user the user
     * @return the user info
     */
    UserInfo select(User user);
}
