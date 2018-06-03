package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface VillageItemProjection {
    Integer getGroupId();
    String getTitle();
    Timestamp getStartTime();
    Timestamp getEndTime();
    Integer getSponsorId();
}
