package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.dao.projection.UserRankProjection;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    String getRank = "SELECT username, people_score AS peopleScore\n" +
            "FROM user";
    String countRank = "SELECT count(*)\n" +
            "FROM user";
    UserEntity getByOpenid(String openId);

    UserEntity getById(Integer id);

    PersonScoreProjection getScoreById(Integer id);

    UserInfoProjection getInfoById(Integer id);

    @Query(value = getRank, countQuery = countRank, nativeQuery = true)
    Page<UserRankProjection> findRank(Pageable page);
}
