package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "score")
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer fromUserId;
    private Integer toUserId;
    private Integer memberDayId;
    private Integer score;

    @Column(columnDefinition = "timestamp DEFAULT current_timestamp")
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT current_timestamp ON UPDATE current_timestamp")
    private Timestamp updateTime;


    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public void setMemberDayId(Integer memberDayId) {
        this.memberDayId = memberDayId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberDayId() {
        return memberDayId;
    }

    public Integer getScore() {
        return score;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
