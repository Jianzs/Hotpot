package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.TaskItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskItemDao extends JpaRepository<TaskItemEntity, Integer> {
}
