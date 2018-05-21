package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.TaskItemDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskItemDayDao extends JpaRepository<TaskItemDayEntity, Integer> {
}
