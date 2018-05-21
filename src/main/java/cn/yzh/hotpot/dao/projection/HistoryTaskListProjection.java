package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface HistoryTaskListProjection {
    Integer getGroupId();
    String getTitle();
    Timestamp getEndTime();
    Integer getFinishedPeople();
    Integer getUnfinishedDay();
}
