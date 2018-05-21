package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDao extends JpaRepository<ScoreEntity, Integer> {
}
