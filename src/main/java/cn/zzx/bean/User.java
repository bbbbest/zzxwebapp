package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用户类
 *
 * @author gloria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Validatable {
    private Integer userId;
    private String username;
    private String password;
    private String name;
    private Integer cardNumber;
    private Integer score;
    private String phone;
    private Byte status;
    private BigDecimal balance;

    @Override
    public boolean canInsert() {
        return userId == null && ObjectUtil.allNonNull(username, password, name, phone, status, balance);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.allNonNull(userId, username, password, name, phone, status, balance);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(userId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.anyNonNull(userId, username, phone, cardNumber);
    }
}
