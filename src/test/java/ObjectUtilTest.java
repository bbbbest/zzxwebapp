import cn.zzx.bean.Admin;
import cn.zzx.util.JsonWrapper;
import net.sf.json.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cn.zzx.util.ObjectUtil.*;

/**
 * @author fzh
 * @since 2017/10/23
 */
public class ObjectUtilTest {
    @Test
    public void test() throws Exception {
        Object a = null;
        Object b = null;
        Object c = null;
        Object d = null;
        System.out.println(allNull(a, b, c, d));
        System.out.println(anyNonNull(a, b, c, d));
        System.out.println(leastOneNull(a, b, c, d));
        a = new Object();
        System.out.println(leastOneNull(a, b, c, d));
        b = new Object();
        c = new Object();
        d = new Object();
        System.out.println(allNull(a, b, c, d));
        System.out.println(anyNonNull(a, b, c, d));
        System.out.println(leastOneNull(a, b, c, d));
    }

    @Test
    public void interfaceTest() throws Exception {
        Admin admin = new Admin();
        Demo demo = new Demo(admin);
        System.out.println(JsonWrapper.parseFromObject(demo));
    }

    @Test
    public void testList2Json() throws Exception {
        List<Integer> list = new ArrayList<>();
        JSONArray array = JsonWrapper.parseFromListOfBaseType("key", list);
        Object[] objects = array.toArray();
        for (Object o : objects) {
            System.out.println(o);
        }
    }

    static class Demo {
        Admin admin;

        public Demo(Admin admin) {
            this.admin = admin;
        }
    }
}
