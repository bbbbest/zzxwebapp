package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易记录对应的bean
 *
 * @author dsf
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealRecord implements Validatable {
    private String dealRecordId;
    private Integer userId;
    private Byte actionType;
    private BigDecimal money;
    private Date actionTime;
    private BigDecimal curBalance;
    /**
     * 支付宝状态，0为不使用支付宝，1为发起支付但未经过成功验证，2支付成功
     */
    private String alipayStatus;

    @Override
    public boolean canInsert() {
        return dealRecordId != null && ObjectUtil.nonNull(userId, actionType, money, actionTime, curBalance, alipayStatus) && actionType == 1;
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.nonNull(dealRecordId, alipayStatus);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(dealRecordId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.anyNonNull(dealRecordId, userId);
    }
}
