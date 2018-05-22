package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.TaskMemberDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TaskMemberDayDao extends JpaRepository<TaskMemberDayEntity, Integer> {
    TaskMemberDayEntity getByGroupIdAndUserIdAndCurrentDay(Integer groupId, Integer userId, Timestamp currentDay);
}
