package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "task_item")
public class TaskItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer groupId;

    private String content;

    @Column(columnDefinition = "timestamp DEFAULT current_timestamp")
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT current_timestamp ON UPDATE current_timestamp")
    private Timestamp updateTime;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupId;
    }


    public String getContent() {
        return content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
