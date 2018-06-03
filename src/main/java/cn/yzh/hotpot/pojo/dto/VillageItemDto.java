package cn.yzh.hotpot.pojo.dto;

import cn.yzh.hotpot.dao.projection.GroupDetailMember;
import cn.yzh.hotpot.dao.projection.VillageItemProjection;

import java.sql.Timestamp;
import java.util.List;

public class VillageItemDto {
    private Integer groupId;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String sponsorName;
    private String sponsorAvatar;
    private List<GroupDetailMember> members;

    public VillageItemDto(VillageItemProjection village) {
        this.groupId = village.getGroupId();
        this.title = village.getTitle();
        this.startTime = village.getStartTime();
        this.endTime = village.getEndTime();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorAvatar() {
        return sponsorAvatar;
    }

    public void setSponsorAvatar(String sponsorAvatar) {
        this.sponsorAvatar = sponsorAvatar;
    }

    public List<GroupDetailMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupDetailMember> members) {
        this.members = members;
    }
}
