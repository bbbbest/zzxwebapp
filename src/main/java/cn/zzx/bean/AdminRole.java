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
public class AdminRole implements Validatable {
    private Integer adminRoleId;
    private Boolean userQuery;
    private Boolean userUpdate;
    private Boolean activityQuery;
    private Boolean activityUpdate;
    private Boolean adminQuery;
    private Boolean adminUpdate;
    private Boolean adviceQuery;
    private Boolean adviceUpdate;
    private Boolean bicycleQuery;
    private Boolean bicycleUpdate;
    private Boolean cyclingRecordQuery;
    private Boolean cyclingRecordUpdate;
    private Boolean dealRecordQuery;
    private Boolean dealRecordUpdate;

    @Override
    public boolean canInsert() {
        return adminRoleId == null && ObjectUtil.allNonNull(
                userQuery, userUpdate,
                activityQuery, activityUpdate,
                adminQuery, adminUpdate,
                adviceQuery, adviceUpdate,
                bicycleQuery, bicycleUpdate,
                cyclingRecordQuery, cyclingRecordUpdate,
                dealRecordQuery, dealRecordUpdate);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.allNonNull(adminRoleId,
                userQuery, userUpdate,
                activityQuery, activityUpdate,
                adminQuery, adminUpdate,
                adviceQuery, adviceUpdate,
                bicycleQuery, bicycleUpdate,
                cyclingRecordQuery, cyclingRecordUpdate,
                dealRecordQuery, dealRecordUpdate);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(adminRoleId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(adminRoleId);
    }
}

