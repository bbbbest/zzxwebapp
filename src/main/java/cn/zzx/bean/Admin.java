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
public class Admin implements Validatable {
    private Integer adminId;
    private String username;
    private String password;
    private String name;
    private Integer adminRoleId;

    @Override
    public boolean canInsert() {
        return adminId == null && ObjectUtil.nonNull(username, password, name);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.nonNull(adminId) && ObjectUtil.anyNonNull(username, password, name);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(adminId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(adminId);
    }
}
