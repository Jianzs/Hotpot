package cn.yzh.hotpot.dao.projection;

import java.sql.Timestamp;

public interface UserInfoProjection {
    String getUsername();
    String getAvatar();
    Timestamp getBirthday();
    Integer getGender();
    Integer getGrade();
    String getCollage();
    String getLocation();
}
