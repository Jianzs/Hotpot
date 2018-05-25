package cn.yzh.hotpot.service;

import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.dao.projection.UserRankProjection;
import cn.yzh.hotpot.exception.ConnectWechatException;
import cn.yzh.hotpot.pojo.dto.OptionDto;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.io.UnsupportedEncodingException;

public interface UserService {
    /**
     * 用户登录
     *  如果code错误返回 “1”
     *  如果新用户返回“22”
     */
    OptionDto<Boolean, String> login(String code) throws UnsupportedEncodingException, ConnectWechatException;

    /**
     * 更新个人信息
     */
    void updateInfo(UserEntity user);

    /**
     * 个人主页获得个人和多人积分
     */
    PersonScoreProjection getScore(Integer userId);

    UserInfoProjection getUserInfo(Integer userId);

    Page<UserRankProjection> getRank(Pageable page);
}
