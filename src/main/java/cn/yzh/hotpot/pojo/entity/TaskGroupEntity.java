package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(schema = "hotpot", name = "task_group")
public class TaskGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer type; // person Or people
    private Integer sponsorId;  // 发起人ID

    private Integer totalPeople;
    private Integer totalTask;
    private Integer maxPeople;
    private boolean isPublic;

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updateTime;

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(Integer totalTask) {
        this.totalTask = totalTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public void setTotalPeople(Integer totalPeople) {
        this.totalPeople = totalPeople;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Integer getType() {
        return type;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public Integer getTotalPeople() {
        return totalPeople;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}
