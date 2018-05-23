package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "task_member")
public class TaskMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer groupId;
    private Integer userId;

    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updateTime;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
