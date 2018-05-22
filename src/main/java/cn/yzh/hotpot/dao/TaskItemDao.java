package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.TaskItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemDao extends JpaRepository<TaskItemEntity, Integer> {
    List<TaskItemEntity> findAllByGroupId(Integer groupId);
}
