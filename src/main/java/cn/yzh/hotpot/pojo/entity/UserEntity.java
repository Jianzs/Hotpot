package cn.yzh.hotpot.pojo.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "hotpot", name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String openid;
    private String avatar;
    private Timestamp birthday;
    private Integer gender;
    private Integer grade;
    private String collage;
    private String location;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer personScore;
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer peopleScore;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer status; // 信息是否完善

    @Column(columnDefinition = "timestamp DEFAULT current_timestamp")
    private Timestamp createTime;
    @Column(columnDefinition = "timestamp DEFAULT current_timestamp ON UPDATE current_timestamp")
    private Timestamp updateTime;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPersonScore(Integer personScore) {
        this.personScore = personScore;
    }

    public void setPeopleScore(Integer peopleScore) {
        this.peopleScore = peopleScore;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getOpenid() {
        return openid;
    }

    public String getAvatar() {
        return avatar;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public String getCollage() {
        return collage;
    }

    public String getLocation() {
        return location;
    }

    public Integer getPersonScore() {
        return personScore;
    }

    public Integer getPeopleScore() {
        return peopleScore;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
}
