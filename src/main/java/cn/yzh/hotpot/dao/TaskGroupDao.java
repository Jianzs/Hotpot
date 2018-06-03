package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.GroupDetailSummary;
import cn.yzh.hotpot.dao.projection.VillageItemProjection;
import cn.yzh.hotpot.pojo.entity.TaskGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupDao  extends JpaRepository<TaskGroupEntity, Integer> {
    String getTaskVillage = "SELECT A.id as groupId, A.title, A.start_time AS startTime, A.end_time AS endTime, A.sponsor_id AS sponsorId\n" +
            "FROM task_group AS A";
    String countTaskVillage = "SELECT count(*) FROM task_group";

    TaskGroupEntity getById(Integer id);

    GroupDetailSummary getSummaryById(Integer groupId);

    @Query(value = getTaskVillage, nativeQuery = true, countQuery = countTaskVillage)
    Page<VillageItemProjection> findTaskVillage(Pageable pageable);
}
