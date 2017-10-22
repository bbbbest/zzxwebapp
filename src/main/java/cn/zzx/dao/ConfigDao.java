package cn.zzx.dao;

import cn.zzx.bean.Config;
import org.springframework.stereotype.Repository;

/**
 * The interface Config dao.
 *
 * @author fzh
 * @since 2017 /10/22
 */
@Repository("configDao")
public interface ConfigDao {
    /**
     * Select config.
     *
     * @return the config
     */
    Config select();

    /**
     * Update int.
     *
     * @param config the config
     * @return the int
     */
    int update(Config config);
}
