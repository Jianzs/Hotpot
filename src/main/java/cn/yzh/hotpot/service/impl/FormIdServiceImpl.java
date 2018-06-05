package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.FormIdDao;
import cn.yzh.hotpot.pojo.entity.FormIdEntity;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import cn.yzh.hotpot.service.FormIdService;
import cn.yzh.hotpot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormIdServiceImpl implements FormIdService {
    private FormIdDao formIdDao;
    private UserService userService;

    @Autowired
    public FormIdServiceImpl(FormIdDao formIdDao, UserService userService) {
        this.formIdDao = formIdDao;
        this.userService = userService;
    }

    @Override
    public void uploadFormId(Integer groupId, String formId, Integer userId) {
        FormIdEntity formIdEntity = new FormIdEntity();
        formIdEntity.setFormId(formId);
        formIdEntity.setTargetId(groupId);
        UserEntity user = userService.getById(userId);
        formIdEntity.setOpenId(user.getOpenid());

        formIdDao.save(formIdEntity);
    }
}
