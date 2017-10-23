package cn.zzx.service.impl;

import cn.zzx.bean.Bicycle;
import cn.zzx.dao.BicycleDao;
import cn.zzx.service.BicycleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author fzh
 * @since 2017/10/23
 */
@Service("bicycleService")
public class BicycleServiceImpl implements BicycleService {

    @Resource(name = "bicycleDao")
    private BicycleDao dao;

    @Override
    public Integer findTotal() {
        return dao.selectTotal();
    }

    @Override
    public List<Bicycle> findByPage(int start, int pageSize) {
        return dao.selectByPage(start, pageSize);
    }

    @Override
    public Optional<Bicycle> findById(Integer id) {
        Bicycle bicycle = Bicycle.builder().bicycleId(id).build();
        if (bicycle.canSelect()) {
            bicycle = dao.select(bicycle);
        }
        return Optional.ofNullable(bicycle);
    }

    @Override
    public Optional<Bicycle> findByLockId(Integer lockId) {
        Bicycle bicycle = Bicycle.builder().lockId(lockId).build();
        if (bicycle.canSelect()) {
            bicycle = dao.select(bicycle);
        }
        return Optional.ofNullable(bicycle);
    }

    @Override
    public List<Integer> findIdByPrefix(String prefix) {
        return dao.selectIdByPrefix(prefix);
    }

    @Override
    public List<Integer> findLockIdByPrefix(String prefix) {
        return dao.selectLockIdByPrefix(prefix);
    }

    @Override
    public boolean update(Integer id, Integer lockId, Byte status) {
        Bicycle b = Bicycle.builder().bicycleId(id).lockId(lockId).status(status).build();
        Bicycle d = null;
        if (b.canSelect()) {
            d = dao.select(b);
        }
        if (d == null) {
            return false;
        }
        d.setLockId(lockId);
        d.setStatus(status);
        return d.canUpdate() && dao.updateByIdSelective(d) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        Bicycle b = Bicycle.builder().bicycleId(id).build();
        if (b.canSelect()) {
            b = dao.select(b);
        }
        return b != null && b.canDelete() && dao.delete(b) > 0;
    }

    @Override
    public boolean saveOnRegistered(String username, Integer lockId) {
        // TODO
        return false;
    }

    @Override
    public boolean saveOnUnregistered(String name, String phone, Integer lockId) {
        // TODO
        return false;
    }

    @Override
    public boolean saveOnCompany(Object[] objects) {
        // TODO
        return false;
    }
}
