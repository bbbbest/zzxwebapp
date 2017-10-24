package cn.zzx.service.impl;

import cn.zzx.bean.Bicycle;
import cn.zzx.bean.User;
import cn.zzx.dao.BicycleDao;
import cn.zzx.dao.UserDao;
import cn.zzx.service.BicycleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource(name = "userDao")
    private UserDao userDao;

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
        User uTmp = User.builder().username(username).build();
        Bicycle bTmp = Bicycle.builder().lockId(lockId).build();
        User user = null;
        Bicycle bicycle = null;
        if (uTmp.canSelect() && bTmp.canSelect()) {
            user = userDao.select(uTmp);
            bicycle = dao.select(bTmp);
        }
        if (user == null || bicycle != null) {
            return false;
        }

        bTmp = Bicycle.builder()
                .from(user.getUserId())
                .lockId(lockId)
                .locationX(BigDecimal.ZERO)
                .locationY(BigDecimal.ZERO)
                .photoUrl("")
                .status((byte) 1)
                .energy(100f)
                .build();
        return bTmp.canInsert() && dao.insert(bTmp) > 0;
    }

    @Override
    public boolean saveOnUnregistered(String name, String phone, Integer lockId) {
        User uTmp = User.builder().phone(phone).build();
        Bicycle bTmp = Bicycle.builder().lockId(lockId).build();
        User user = null;
        Bicycle bicycle = null;
        if (uTmp.canSelect() && bTmp.canSelect()) {
            user = userDao.select(uTmp);
            bicycle = dao.select(bTmp);
        }
        if (user != null || bicycle != null) {
            return false;
        }
        uTmp = User.builder()
                .username(phone)
                .password(phone.substring(phone.length() - 6, phone.length()))
                .name(name)
                .phone(phone)
                .score(0)
                .status((byte) 1)
                .balance(BigDecimal.ZERO)
                .build();
        if (uTmp.canInsert()) {
            if (userDao.insert(uTmp) == 0) {
                throw new RuntimeException("User insert failed.");
            }
        }
        bTmp = Bicycle.builder()
                .from(uTmp.getUserId())
                .lockId(lockId)
                .locationX(BigDecimal.ZERO)
                .locationY(BigDecimal.ZERO)
                .photoUrl("")
                .status((byte) 1)
                .energy(100f)
                .build();
        if (bTmp.canInsert()) {
            if (dao.insert(bTmp) == 0) {
                throw new RuntimeException("Bicycle insert failed.");
            }
        }
        return true;
    }

    @Override
    public boolean saveOnCompany(Object[] objects) {
        // TODO
        return false;
    }
}
