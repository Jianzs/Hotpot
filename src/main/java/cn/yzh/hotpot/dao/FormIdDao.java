package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.pojo.entity.FormIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormIdDao extends JpaRepository<FormIdEntity, Integer> {
}
