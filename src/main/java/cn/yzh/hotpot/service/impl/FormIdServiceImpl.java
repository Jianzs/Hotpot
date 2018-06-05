package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.FormIdDao;
import cn.yzh.hotpot.pojo.entity.FormIdEntity;
import cn.yzh.hotpot.service.FormIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormIdServiceImpl implements FormIdService {
    private FormIdDao formIdDao;

    @Autowired
    public FormIdServiceImpl(FormIdDao formIdDao) {
        this.formIdDao = formIdDao;
    }

    @Override
    public void uploadFormId(Integer groupId, String formId) {
        FormIdEntity formIdEntity = new FormIdEntity();
        formIdEntity.setFormId(formId);
        formIdEntity.setTargetId(groupId);
        formIdDao.save(formIdEntity);
    }
}
