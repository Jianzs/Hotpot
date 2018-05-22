package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.UserDao;
import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.enums.UserGenderEnum;
import cn.yzh.hotpot.enums.UserGradeEnum;
import cn.yzh.hotpot.enums.UserInfoEnum;
import cn.yzh.hotpot.enums.UserRoleEnum;
import cn.yzh.hotpot.exception.ConnectWechatException;
import cn.yzh.hotpot.pojo.dto.OptionDto;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import cn.yzh.hotpot.service.UserService;
import cn.yzh.hotpot.util.DatetimeUtil;
import cn.yzh.hotpot.util.JWTUtil;
import cn.yzh.hotpot.util.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public OptionDto<Boolean, String> login(String code)
            throws UnsupportedEncodingException, ConnectWechatException {
        String openId = WechatUtil.getOpenId(code);
        if (openId == null) return null;

        UserEntity user = userDao.getByOpenid(openId);
        if (user == null) {
            user = new UserEntity();
            user.setAvatar("https://avatars0.githubusercontent.com/u/20160766?s=460&v=4");
            user.setUsername("Stranger");
            user.setCollage("Collage");
            user.setGender(UserGenderEnum.MALE.getValue());
            user.setGrade(UserGradeEnum.COLLEGE_ONE.getValue());
            user.setLocation("中国");
            user.setBirthday(DatetimeUtil.getTodayNoonTimestamp());

            user.setOpenid(openId);
            user.setPersonScore(0);
            user.setPeopleScore(0);
            user.setStatus(UserInfoEnum.UNCOMPLETED.getValue());
            user = userDao.save(user);
        }
        Boolean isNew = user.getStatus().equals(UserInfoEnum.UNCOMPLETED.getValue());

        return new OptionDto<>(isNew, JWTUtil.createToken(user.getId(), UserRoleEnum.ORDINARY_USER.getValue()));
    }

    @Override
    public void updateInfo(UserEntity user) {
        UserEntity oldUser = userDao.getById(user.getId());
        user.setPeopleScore(oldUser.getPeopleScore());
        user.setPersonScore(oldUser.getPersonScore());
        user.setOpenid(oldUser.getOpenid());
        user.setStatus(UserInfoEnum.COMPLETED.getValue());
        userDao.save(user);
    }

    @Override
    public PersonScoreProjection getScore(Integer userId) {
        return userDao.getScoreById(userId);
    }

    @Override
    public UserInfoProjection getUserInfo(Integer userId) {
        return userDao.getInfoById(userId);
    }
}
