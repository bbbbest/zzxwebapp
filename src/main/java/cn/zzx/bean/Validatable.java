package cn.zzx.bean;

/**
 * 此接口用于验证数据的数据库可操作性
 *
 * @author fzh
 * @since 2017 /10/22
 */
public interface Validatable {
    /**
     * 是否可插入
     *
     * @return boolean
     */
    boolean canInsert();

    /**
     * 是否可更新
     *
     * @return the boolean
     */
    boolean canUpdate();

    /**
     * 是否可删除
     *
     * @return the boolean
     */
    boolean canDelete();

    /**
     * 是否可查找
     *
     * @return the boolean
     */
    boolean canSelect();
}
