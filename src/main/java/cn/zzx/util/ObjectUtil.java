package cn.zzx.util;

import org.jetbrains.annotations.Contract;

/**
 * 这个类用来判断判断对象数组的逻辑情况
 *
 * @author fzh
 * @since 2017 /10/22
 */
public class ObjectUtil {

    /**
     * 判断传入对象是否<b>不为空</b>
     *
     * @param object 待判断对象
     * @return 判断结果
     */
    @Contract(value = "null -> false; !null -> true", pure = true)
    public static boolean nonNull(Object object) {
        return object != null;
    }

    /**
     * 判断传输数组是否<b>全不为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean allNonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断传输数组是否<b>全为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean allNull(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断传输数组是否<b>至少有一个不为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean anyNonNull(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断传输数组是否<b>至少有一个为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean anyNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断传输数组是否至少<b>有一个为空但不全不为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean leastOneNull(Object... objects) {
        return !allNonNull(objects) && anyNonNull(objects);
    }

    /**
     * 判断传输数组是否<b>至少有一个不为空但不全为空</b>
     *
     * @param objects 待判断数组
     * @return 判断结果
     */
    @Contract(pure = true)
    public static boolean leastOneNonNull(Object... objects) {
        return !allNull(objects) && anyNull(objects);
    }

    /**
     * 要求传入数组全不为空，否则抛出空指针异常
     *
     * @param objects 待判断数组
     */
    public static void requireAllNonNull(Object... objects) {
        if (!allNonNull(objects)) {
            throw new NullPointerException("Required non null, but found one.");
        }
    }
}
