package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.FormIdDao;
import cn.yzh.hotpot.dao.projection.GroupDetailItem;
import cn.yzh.hotpot.message.CreateSuccMessage;
import cn.yzh.hotpot.message.JoinSuccMessage;
import cn.yzh.hotpot.pojo.entity.FormIdEntity;
import cn.yzh.hotpot.pojo.entity.TaskGroupEntity;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import cn.yzh.hotpot.service.FormIdService;
import cn.yzh.hotpot.service.TaskService;
import cn.yzh.hotpot.service.UserService;
import cn.yzh.hotpot.util.SendMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FormIdServiceImpl implements FormIdService {
    private FormIdDao formIdDao;
    private UserService userService;

    private TaskService taskService;

    @Autowired
    public FormIdServiceImpl(FormIdDao formIdDao, UserService userService, TaskService taskService) {
        this.formIdDao = formIdDao;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public void uploadFormId(Integer groupId, String formId, Integer userId) {
        UserEntity user = userService.getById(userId);
        TaskGroupEntity group = taskService.getById(groupId);
        List<GroupDetailItem> items = taskService.getItemsByGroupId(groupId);

        // 保存 formId
        FormIdEntity formIdEntity = new FormIdEntity();
        formIdEntity.setFormId(formId);
        formIdEntity.setTargetId(groupId);
        formIdEntity.setOpenId(user.getOpenid());
        formIdDao.save(formIdEntity);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            GroupDetailItem item = items.get(i);
            sb.append(i+1).append(". ").append(item.getContent()).append("\\n");
        }

        String message;
        if (userId.equals(group.getSponsorId())) {
            message = CreateSuccMessage.createMessage(user.getOpenid(),
                    groupId,
                    formId,
                    group.getTitle(),
                    group.getEndTime(),
                    group.getTotalPeople(),
                    sb.toString());
        } else {
            message = JoinSuccMessage.createMessage(user.getOpenid(),
                    groupId,
                    formId,
                    group.getTitle(),
                    new Date(),
                    sb.toString());
        }

        System.out.println(message);
        SendMessageUtil.send(message);
    }
}
