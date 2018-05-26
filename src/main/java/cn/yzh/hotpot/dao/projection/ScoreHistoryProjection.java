package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface ScoreHistoryProjection {
    Integer getScore();
    String getTitle();
    Timestamp getCurrentDay();
}
