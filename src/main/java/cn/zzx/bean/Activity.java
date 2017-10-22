package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 活动类
 *
 * @author gloria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Validatable {
    private Integer activityId;
    private Integer userId;
    private String title;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String description;

    /**
     * -1代表未被阅读（负数代表未被阅读，正数代表以被阅读）
     * --        1代表已被阅读但未回复
     * --        2代表已被阅读并已准许
     * -- 		 3代表已被阅读并已回绝
     */
    private Byte status;
    private String replyContent;

    @Override
    public boolean canInsert() {
        return activityId == null && ObjectUtil.nonNull(userId, title, createTime, startTime, endTime, description);
    }

    @Override
    public boolean canUpdate() {
        return ObjectUtil.nonNull(activityId, userId, title, createTime, startTime, endTime, description);
    }

    @Override
    public boolean canDelete() {
        return ObjectUtil.nonNull(activityId);
    }

    @Override
    public boolean canSelect() {
        return ObjectUtil.nonNull(activityId);
    }
}
