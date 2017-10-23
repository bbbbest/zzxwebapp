package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fzh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advice implements Validatable {
    private Integer adviseId;
    private Integer adminId;
    private Integer creator;
    private String title;
    private String content;
    private Date createTime;
    private Byte status;
    private Date replyTime;
    private String replyContent;

    @Override
    public boolean canInsert() {
        return adviseId == null && ObjectUtil.allNonNull(creator, title, content, createTime, status);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.allNonNull(adviseId, adminId, creator, title, content, createTime, status, replyTime, replyContent);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(adviseId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(adviseId);
    }
}
