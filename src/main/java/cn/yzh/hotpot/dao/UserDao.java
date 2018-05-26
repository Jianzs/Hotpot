package cn.yzh.hotpot.dao;

import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.ScoreHistoryProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.dao.projection.UserRankProjection;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    String getRank = "SELECT username, people_score AS peopleScore\n" +
            "FROM user";
    String countRank = "SELECT count(*)\n" +
            "FROM user";
    String getScoreHistory = "SELECT A.current_day AS currentDay, A.score, B.title\n" +
            "FROM (SELECT group_id, current_day, score\n" +
            "    FROM task_member_day\n" +
            "    WHERE user_id = :userId AND current_day < :curDay\n" +
            "        AND score IS NOT NULL) AS A\n" +
            "    LEFT JOIN task_group AS B ON (A.group_id = B.id)\n";
    String countScoreHistory = "SELECT count(*)\n" +
            "FROM task_member_day\n" +
            "WHERE user_id = :userId AND current_day < :curDay\n" +
            "    AND score IS NOT NULL";

    UserEntity getByOpenid(String openId);

    UserEntity getById(Integer id);

    PersonScoreProjection getScoreById(Integer id);

    UserInfoProjection getInfoById(Integer id);

    @Query(value = getRank, countQuery = countRank, nativeQuery = true)
    Page<UserRankProjection> findRank(Pageable page);

    @Query(value = getScoreHistory, countQuery = countScoreHistory, nativeQuery = true)
    Page<ScoreHistoryProjection> getScoreHistory(@Param("userId") Integer userId,
                                                 @Param("curDay")Timestamp curDay,
                                                 Pageable page);
}
