package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.GroupDetailSummary;
import cn.yzh.hotpot.pojo.entity.TaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupDao  extends JpaRepository<TaskGroupEntity, Integer> {
    TaskGroupEntity getById(Integer id);

    GroupDetailSummary getSummaryById(Integer groupId);
}
