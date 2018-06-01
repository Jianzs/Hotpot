package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface NotStartedTaskListProjection {
    Integer getGroupId();
    String getTitle();
    Integer getType();
    Timestamp getStartTime();
}
