package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.GroupDetailMember;
import cn.yzh.hotpot.pojo.entity.TaskMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMemberDao extends JpaRepository<TaskMemberEntity, Integer> {
    String findMemberByGroupId = "SELECT avatar, username\n" +
            "FROM user\n" +
            "WHERE id IN (SELECT user_id\n" +
            "    FROM task_member\n" +
            "    WHERE group_id = :groupId)";

    boolean existsByUserIdAndGroupId(Integer userId, Integer groupId);

    @Query(value = findMemberByGroupId, nativeQuery = true)
    List<GroupDetailMember> findAllMemberOfGroup(@Param("groupId") Integer groupId);
}
