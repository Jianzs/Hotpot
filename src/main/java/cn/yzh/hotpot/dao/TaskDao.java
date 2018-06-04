package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.HistoryTaskListProjection;
import cn.yzh.hotpot.dao.projection.NotStartedTaskListProjection;
import cn.yzh.hotpot.dao.projection.PendingTaskListProjection;
import cn.yzh.hotpot.pojo.entity.TaskGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<TaskGroupEntity, Integer> {
    String getNotStartedTaskList = "SELECT id AS groupId, start_time AS startTime, title, type, total_task AS totalTask\n" +
            "FROM task_group\n" +
            "WHERE start_time >= :todayMoon AND\n" +
            "    id IN (SELECT group_id\n" +
            "        FROM task_member\n" +
            "        WHERE user_id = :userId)\n" +
            "ORDER BY start_time";
    String getPendingTaskList = "SELECT C.group_id AS groupId, C.title, C.type, C.end_time AS endTime, C.total_task AS totalTask, IFNULL(finishedPeople, 0) AS finishedPeople, C.finished_task AS finishedTask\n" +
            "FROM (SELECT DISTINCT group_id, finished_task, title, type, end_time, total_task, start_time\n" +
            "    FROM task_member_day LEFT JOIN task_group ON (task_member_day.group_id = task_group.id)\n" +
            "    WHERE end_time >= :curTime\n" +
            "        AND user_id = :userId\n" +
            "        AND current_day = :curDay) AS C\n" +
            "    LEFT JOIN (SELECT A.id AS group_id, count(*) AS finishedPeople\n" +
            "    FROM task_group AS A \n" +
            "        LEFT JOIN \n" +
            "        (SELECT group_id, finished_task\n" +
            "            FROM task_member_day\n" +
            "            WHERE current_day = :curDay) AS B ON (A.id = B.group_id)\n" +
            "    WHERE A.end_time >= :curTime\n" +
            "        AND A.total_task = B.finished_task\n" +
            "    GROUP BY B.group_id) AS D ON (C.group_id = D.group_id)\n" +
            "ORDER BY time(C.end_time)";

    String countHistoryTaskList = "select count(*) \n" +
            "FROM task_member AS A LEFT JOIN task_group AS B ON (A.group_id = B.id)\n" +
            "WHERE B.end_time < :curTime AND user_id = :userId";

    String getHistoryTaskList = "SELECT I.group_id AS groupId, I.title, I.type, I.end_time AS endTime, I.finished_people AS finishedPeople, IFNULL(J.unfinished_day, 0) AS unfinishedDay\n" +
            "FROM (SELECT F.id AS group_id, F.title, F.type, F.end_time, F.total_people - E.unfinished_people AS finished_people\n" +
            "    FROM (SELECT DISTINCT C.group_id, count(DISTINCT D.user_id) AS unfinished_people\n" +
            "        FROM (SELECT B.id AS group_id, B.total_task\n" +
            "            FROM task_member AS A LEFT JOIN task_group AS B ON (A.group_id = B.id)\n" +
            "            WHERE B.end_time < :curTime AND user_id = :userId) AS C\n" +
            "            LEFT JOIN task_member_day AS D ON (C.group_id = D.group_id)\n" +
            "        WHERE finished_task < C.total_task\n" +
            "        GROUP BY D.group_id) AS E\n" +
            "        LEFT JOIN task_group AS F ON (E.group_id = F.id)) AS I\n" +
            "    LEFT JOIN (SELECT G.id AS group_id, count(*) AS unfinished_day\n" +
            "        FROM task_group AS G LEFT JOIN task_member_day AS H ON (G.id = H.group_id)\n" +
            "    WHERE user_id = :userId AND finished_task < total_task\n" +
            "    GROUP BY G.id) AS J ON (I.group_id = J.group_id)\n";


    @Query(value = getPendingTaskList, nativeQuery = true)
    List<PendingTaskListProjection> getPendingTaskList(@Param("curTime") Timestamp curTime,
                                                       @Param("curDay") Timestamp curDay,
                                                       @Param("userId") Integer userId);

    @Query(value = getHistoryTaskList, countQuery = countHistoryTaskList, nativeQuery = true)
    Page<HistoryTaskListProjection> getHistoryTaskList(@Param("curTime") Timestamp curTime,
                                                       @Param("userId") Integer userId,
                                                       Pageable pageable);

    @Query(value = getNotStartedTaskList, nativeQuery = true)
    List<NotStartedTaskListProjection> findNotStartedTaskList(@Param("userId") Integer userId,
                                                              @Param("todayMoon") Timestamp todayMoon);
}
