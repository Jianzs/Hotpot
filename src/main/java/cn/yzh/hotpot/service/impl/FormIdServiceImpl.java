package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.FormIdDao;
import cn.yzh.hotpot.dao.projection.GroupDetailItem;
import cn.yzh.hotpot.enums.FormIdStatusEnum;
import cn.yzh.hotpot.enums.UploadFormIdTypeEum;
import cn.yzh.hotpot.message.AddGroupSuccMessage;
import cn.yzh.hotpot.pojo.entity.FormIdEntity;
import cn.yzh.hotpot.pojo.entity.TaskGroupEntity;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import cn.yzh.hotpot.service.FormIdService;
import cn.yzh.hotpot.service.TaskService;
import cn.yzh.hotpot.service.UserService;
import cn.yzh.hotpot.util.SendMessageUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void uploadFormId(JSONObject jsonObject, Integer userId) {
        String formId = jsonObject.getString("formId");
        UserEntity user = userService.getById(userId);

        if (UploadFormIdTypeEum.JUST_UPLOAD.getValue().equals(jsonObject.getInt("type"))) {
            // 保存 formId
            FormIdEntity formIdEntity = new FormIdEntity();
            formIdEntity.setFormId(formId);
            formIdEntity.setOpenId(user.getOpenid());
            formIdEntity.setUserId(userId);
            formIdEntity.setStatus(FormIdStatusEnum.UNUSED.getValue());
            formIdDao.save(formIdEntity);
        } else {
            Integer groupId = jsonObject.getInt("groupId");

            TaskGroupEntity group = taskService.getById(groupId);
            List<GroupDetailItem> items = taskService.getItemsByGroupId(groupId);

            String message = AddGroupSuccMessage.createMessage(user.getOpenid(),
                    group.getId(),
                    formId,
                    group.getTitle(),
                    group.getStartTime(),
                    group.getEndTime(),
                    group.getTotalPeople(),
                    items);

            System.out.println(message);
            SendMessageUtil.send(message);
        }
    }
}
