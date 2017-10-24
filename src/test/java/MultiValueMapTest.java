import cn.zzx.bean.AdminRole;
import cn.zzx.dao.AdminRoleDao;
import cn.zzx.dao.AdviceDao;
import cn.zzx.dao.BicycleDao;
import cn.zzx.dao.UserDao;
import cn.zzx.service.BicycleService;
import cn.zzx.util.AdminRoleParser;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author fzh
 * @since 2017/10/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/springApplication.xml")
public class MultiValueMapTest {

    @Qualifier("adminRoleDao")
    @Autowired
    private AdminRoleDao dao;

    @Qualifier("adviceDao")
    @Autowired
    private AdviceDao adviceDao;

    @Qualifier("bicycleDao")
    @Autowired
    private BicycleDao bicycleDao;

    @Qualifier("userDao")
    @Autowired
    private UserDao userDao;

    @Autowired
    private BicycleService bicycleService;

    @Test
    public void mvmTest() throws Exception {
        final JSONArray ja = new JSONArray();
        List<AdminRole> lar = dao.selectAll();
        for (AdminRole role : lar) {
            ja.add(AdminRoleParser.parse(role));
        }
        System.out.println(ja);
    }

    @Test
    public void liii() throws Exception {
        System.out.println(adviceDao.selectByPage(0, 10));
//        adviceDao.delete(Advice.builder().adviseId(1).build());
    }

    @Test
    public void bicycle() throws Exception {
        System.out.println(bicycleDao.selectIdByPrefix("1"));
    }

    @Test
    public void userTest() throws Exception {
        System.out.println(bicycleService.saveOnUnregistered("Honey", "15856856565", 5));
    }
}
