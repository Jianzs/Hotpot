package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    UserEntity getByOpenid(String openId);

    UserEntity getById(Integer id);

    PersonScoreProjection getScoreById(Integer id);

    UserInfoProjection getInfoById(Integer id);
}
