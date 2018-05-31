package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.GroupDetailItem;
import cn.yzh.hotpot.enums.TaskFinishStatusEnum;
import cn.yzh.hotpot.pojo.entity.TaskItemDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskItemDayDao extends JpaRepository<TaskItemDayEntity, Integer> {
    String getContent="SELECT content, id AS itemId\n" +
            "FROM task_item\n" +
            "WHERE id IN (SELECT item_id\n" +
            "    FROM task_item_day\n" +
            "    WHERE user_id = :userId\n" +
            "        AND current_day = :curDay\n" +
            "        AND status = :status)\n" +
            "    AND group_id = :groupId";


    TaskItemDayEntity getByItemIdAndUserIdAndCurrentDay(Integer itemId, Integer userId, Timestamp currentDay);

    @Query(value = getContent, nativeQuery = true)
    List<GroupDetailItem> getContentByGroupIdAndUserIdAndCurrentDayAndStatus(@Param("groupId") Integer groupId,
                                                                             @Param("userId") Integer userId,
                                                                             @Param("curDay") Timestamp currentDay,
                                                                             @Param("status") Integer status);
}
