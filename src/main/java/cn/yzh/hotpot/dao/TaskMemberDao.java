package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.TaskMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMemberDao extends JpaRepository<TaskMemberEntity, Integer> {
}
