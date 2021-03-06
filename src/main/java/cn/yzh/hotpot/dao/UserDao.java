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
    String getRank = "SELECT username, avatar, people_score AS peopleScore\n" +
            "FROM user";
    String countRank = "SELECT count(*)\n" +
            "FROM user";
    String getScoreHistory = "SELECT C.current_day AS currentDay, C.score, C.title, D.avatar AS sponsorAvatar\n" +
            "FROM (SELECT A.current_day, A.score, B.title, B.sponsor_id, B.type\n" +
            "    FROM (SELECT group_id, current_day, score\n" +
            "        FROM task_member_day\n" +
            "        WHERE user_id = :userId AND \n" +
            "            current_day < :curDay AND \n" +
            "            score IS NOT NULL) AS A\n" +
            "        LEFT JOIN task_group AS B ON (A.group_id = B.id)\n" +
            "    WHERE type = :type) AS C\n" +
            "    LEFT JOIN user AS D ON (C.sponsor_id = D.id)";
    String countScoreHistory = "SELECT count(*)\n" +
            "FROM (SELECT group_id \n" +
            "    FROM task_member_day\n" +
            "    WHERE user_id = :userId AND \n" +
            "        current_day < :curDay AND \n" +
            "        score IS NOT NULL) AS A\n" +
            "    LEFT JOIN task_group AS B ON (A.group_id = B.id)\n" +
            "    WHERE type = :type";

    UserEntity getByOpenid(String openId);

    UserEntity getById(Integer id);

    PersonScoreProjection getScoreById(Integer id);

    UserInfoProjection getInfoById(Integer id);

    @Query(value = getRank, countQuery = countRank, nativeQuery = true)
    Page<UserRankProjection> findRank(Pageable page);

    @Query(value = getScoreHistory, countQuery = countScoreHistory, nativeQuery = true)
    Page<ScoreHistoryProjection> getScoreHistoryByType(@Param("userId") Integer userId,
                                                 @Param("type") Integer type,
                                                 @Param("curDay")Timestamp curDay,
                                                 Pageable page);
}
