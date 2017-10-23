package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fzh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Validatable {
    private String userId;
    private String name;
    private String school;
    private String major;
    private String department;
    private String phone;

    @Override
    public boolean canInsert() {
        return ObjectUtil.allNonNull(userId, name, school, major, department, phone);
    }

    @Override
    public boolean canUpdate() {
        // TODO
        return true;
    }

    @Override
    public boolean canDelete() {
        // TODO
        return true;
    }

    @Override
    public boolean canSelect() {
        // TODO
        return true;
    }
}
