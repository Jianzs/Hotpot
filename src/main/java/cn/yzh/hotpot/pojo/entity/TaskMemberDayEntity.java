package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "task_member_day")
public class TaskMemberDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer groupId;
    private Integer userId;

    private Timestamp currentDay;

    // 完成任务数
    private Integer finishedTask;
    // 今日所得积分，由每日定时任务结算
    private Integer score;

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updateTime;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCurrentDay(Timestamp currentDay) {
        this.currentDay = currentDay;
    }

    public void setFinishedTask(Integer finishedTask) {
        this.finishedTask = finishedTask;
    }

    public Integer getId() {
        return id;
    }


    public Integer getUserId() {
        return userId;
    }

    public Timestamp getCurrentDay() {
        return currentDay;
    }

    public Integer getFinishedTask() {
        return finishedTask;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
