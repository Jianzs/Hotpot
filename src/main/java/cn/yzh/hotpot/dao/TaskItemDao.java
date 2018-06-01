package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.GroupDetailItem;
import cn.yzh.hotpot.pojo.entity.TaskItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskItemDao extends JpaRepository<TaskItemEntity, Integer> {
    String findBriefItem = "SELECT id AS itemId, content\n" +
            "FROM task_item\n" +
            "WHERE group_id = :groupId";

    List<TaskItemEntity> findAllByGroupId(Integer groupId);

    @Query(value = findBriefItem, nativeQuery = true)
    List<GroupDetailItem> findBriefByGroupId(@Param("groupId") Integer groupId);
}
