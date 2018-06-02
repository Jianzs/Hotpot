package cn.yzh.hotpot.dao.projection;

import org.bouncycastle.util.Times;

import java.sql.Timestamp;

public interface GroupDetailSummary {
    Timestamp getEndTime();
    Timestamp getStartTime();
    Integer getType();
    String getTitle();
}
