package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface PendingTaskListProjection {
    Integer getGroupId();
    String getTitle();
    Timestamp getEndTime();
    Integer getFinishedPeople();
    Integer getFinishedTask();
    Integer getTotalTask();
}
