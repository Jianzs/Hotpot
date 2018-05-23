package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "task_item_day")
public class TaskItemDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Integer itemId;
    private Integer status;
    private Timestamp finishedTime;

    private Timestamp currentDay;

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Timestamp finishedTime) {
        this.finishedTime = finishedTime;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCurrentDay(Timestamp currentDay) {
        this.currentDay = currentDay;
    }

    public Integer getId() {
        return id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getStatus() {
        return status;
    }

    public Timestamp getCurrentDay() {
        return currentDay;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
