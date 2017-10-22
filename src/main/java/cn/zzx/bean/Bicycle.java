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
    private Integer status;
    private Float energy;

    @Override
    public boolean canInsert() {
        return bicycleId == null && ObjectUtil.nonNull(from, time, photoUrl, lockId, status, energy);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.nonNull(bicycleId, from, time, photoUrl, lockId, locationX, locationY, status, energy);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(bicycleId, lockId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(bicycleId);
    }
}