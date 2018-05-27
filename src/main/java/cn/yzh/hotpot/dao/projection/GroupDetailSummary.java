package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface GroupDetailSummary {
    Timestamp getEndTime();
    Integer getType();
    String getTitle();
}
