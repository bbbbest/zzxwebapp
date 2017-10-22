package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fzh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CyclingRecord implements Validatable {
    private String cyclingRecordId;
    private Integer bicycleId;
    private Integer userId;
    private Date startTime;
    private Date endTime;
    private BigDecimal startLocX;
    private BigDecimal startLocY;
    private BigDecimal endLocX;
    private BigDecimal endLocY;

    @Override
    public boolean canInsert() {
        return false;
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.nonNull(cyclingRecordId, bicycleId, userId, startTime, endTime, startLocX, startLocY, endLocX, endLocY);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(cyclingRecordId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(cyclingRecordId);
    }
}