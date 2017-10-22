package cn.zzx.util;

/**
 * @author fzh
 * @since 2017/10/22
 */
public class ObjectUtil {
    public static boolean nonNull(Object object) {
        return object != null;
    }

    public static boolean nonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean anyNonNull(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                return true;
            }
        }
        return false;
    }

    public static void requireNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new NullPointerException("Required non null, but found one.");
            }
        }
    }
}
