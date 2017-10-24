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
public class Bicycle implements Validatable {
    private Integer bicycleId;
    private Integer from;
    private Date time;
    private String photoUrl;
    private Integer lockId;
    private BigDecimal locationX;
    private BigDecimal locationY;
    private Byte status;
    private Float energy;

    @Override
    public boolean canInsert() {
        return bicycleId == null && ObjectUtil.allNonNull(from, photoUrl, lockId, status, energy);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.allNonNull(bicycleId, from, time, photoUrl, lockId, locationX, locationY, status, energy);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.allNonNull(bicycleId, lockId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.leastOneNonNull(bicycleId, lockId);
    }
}