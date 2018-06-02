package cn.yzh.hotpot.service.impl;

import cn.yzh.hotpot.dao.*;
import cn.yzh.hotpot.dao.projection.*;
import cn.yzh.hotpot.enums.TaskFinishStatusEnum;
import cn.yzh.hotpot.enums.TaskGroupTypeEnum;
import cn.yzh.hotpot.exception.NoSuchMemberInGroup;
import cn.yzh.hotpot.exception.NoSuchTaskMemberDay;
import cn.yzh.hotpot.pojo.dto.OptionDto;
import cn.yzh.hotpot.pojo.entity.*;
import cn.yzh.hotpot.service.TaskService;
import cn.yzh.hotpot.util.DatetimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskDao taskDao;
    private TaskGroupDao taskGroupDao;
    private TaskItemDao taskItemDao;
    private TaskItemDayDao taskItemDayDao;
    private TaskMemberDao taskMemberDao;
    private TaskMemberDayDao taskMemberDayDao;
    private ScoreDao scoreDao;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao,
                           TaskGroupDao taskGroupDao,
                           TaskItemDao taskItemDao,
                           TaskItemDayDao taskItemDayDao,
                           TaskMemberDao taskMemberDao,
                           TaskMemberDayDao taskMemberDayDao,
                           ScoreDao scoreDao) {
        this.taskDao = taskDao;
        this.taskGroupDao = taskGroupDao;
        this.taskItemDao = taskItemDao;
        this.taskItemDayDao = taskItemDayDao;
        this.taskMemberDao = taskMemberDao;
        this.taskMemberDayDao = taskMemberDayDao;
        this.scoreDao = scoreDao;
    }

    @Override
    public List<PendingTaskListProjection> getPendingTaskList(Integer userId) {

        return taskDao.getPendingTaskList(DatetimeUtil.getNowTimestamp(),
                DatetimeUtil.getTodayNoonTimestamp(),
                userId);
    }

    @Override
    public Page<HistoryTaskListProjection> getHistoryTaskList(Integer userId, Pageable pageable) {

        return taskDao.getHistoryTaskList(DatetimeUtil.getNowTimestamp(),
                userId,
                pageable);
    }

    @Override
    public Integer createTaskGroup(JSONObject jsonObject, Integer userId) {
        JSONArray items = jsonObject.getJSONArray("items");
        // 新建一个任务组
        TaskGroupEntity taskGroup = buildTaskGroup(jsonObject, userId, items.length());
        TaskGroupEntity saveGroup = taskDao.save(taskGroup);

        // 添加任务列表
        for (int i = 0; i < items.length(); i++) {
            // 添加任务内容列表
            TaskItemEntity taskItem = buildTaskItem(items.getJSONObject(i), saveGroup.getId());
            TaskItemEntity saveItem = taskItemDao.save(taskItem);
            // 添加任务每天列表
            List<TaskItemDayEntity> taskItemDays = buildTaskItemDays(saveItem, userId,
                    saveGroup.getStartTime(),
                    saveGroup.getEndTime());
            taskItemDayDao.saveAll(taskItemDays);
        }

        // 添加成员信息
        TaskMemberEntity member = buildTaskMember(userId, saveGroup.getId());
        TaskMemberEntity saveMember = taskMemberDao.save(member);
        // 添加任务内每日成员信息
        List<TaskMemberDayEntity> members = buildTaskMemberDays(saveMember,
                saveGroup.getStartTime(),
                saveGroup.getEndTime());
        taskMemberDayDao.saveAll(members);

        return saveGroup.getId();
    }

    @Override
    public OptionDto<Integer, String> finishTaskItem(JSONObject jsonObject, Integer userId) {
        Integer itemId = jsonObject.getInt("itemId");
        Integer groupId = jsonObject.getInt("groupId");

        TaskGroupEntity taskGroup = taskGroupDao.getById(groupId);
        if (DatetimeUtil.compareTime(new Date(), taskGroup.getEndTime()) > 0) {
            return new OptionDto<>(5, "Today Task is Over.");
        }
        if (taskGroup.getEndTime().compareTo(DatetimeUtil.getNowTimestamp()) < 0) {
            return new OptionDto<>(2, "Task Group is Over.");
        }

        // 更新某一项任务完成状态
        TaskItemDayEntity itemDay = taskItemDayDao.getByItemIdAndUserIdAndCurrentDay(itemId, userId, DatetimeUtil.getTodayNoonTimestamp());
        if (itemDay == null) {
            return new OptionDto<>(3, "Task Item Not Exist.");
        }
        if (itemDay.getStatus().equals(TaskFinishStatusEnum.FINISHED.getValue()))
            return new OptionDto<>(1, "Task Already is Finished.");
        itemDay.setStatus(TaskFinishStatusEnum.FINISHED.getValue());
        itemDay.setFinishedTime(DatetimeUtil.getNowTimestamp());
        taskItemDayDao.save(itemDay);

        // 更新某用户某一天任务完成数
        TaskMemberDayEntity memberDay = taskMemberDayDao.getByGroupIdAndUserIdAndCurrentDay(groupId, userId,
                DatetimeUtil.getTodayNoonTimestamp());
        if (memberDay == null) {
            return new OptionDto<>(4, "Task Member Not Exist.");
        }
        memberDay.setFinishedTask(memberDay.getFinishedTask() + 1);
        taskMemberDayDao.save(memberDay);
        return null;
    }

    @Override
    public OptionDto<Integer, String> joinTaskGroup(JSONObject jsonObject, Integer userId) {
        Integer groupId = jsonObject.getInt("groupId");

        TaskGroupEntity group = taskGroupDao.getById(groupId);

        // 判断是否还能容纳下更多的人
        Integer totalPeople = group.getTotalPeople();
        Integer maxPeople = group.getMaxPeople();
        if (totalPeople >= maxPeople) {
            return new OptionDto<>(1, "The Number is Full");
        }
        if (group.getEndTime().compareTo(DatetimeUtil.getNowTimestamp()) < 0) {
            return new OptionDto<>(2, "Task Group is Over.");
        }
        if (taskMemberDao.existsByUserIdAndGroupId(userId, groupId)) {
            return new OptionDto<>(3, "Already in Group.");
        }
        // 人数加 1
        group.setTotalPeople(group.getTotalPeople() + 1);
        taskGroupDao.save(group);

        Timestamp startTime = group.getStartTime().compareTo(DatetimeUtil.getNowTimestamp()) > 0 ?
                group.getStartTime() : DatetimeUtil.getNowTimestamp();

        // 添加任务项，若任务未开始，从任务开始日起添加，若任务已经开始，从当前天添加
        List<TaskItemEntity> taskItems = taskItemDao.findAllByGroupId(groupId);
        taskItems.forEach((item) -> {
            List<TaskItemDayEntity> taskItemDays = buildTaskItemDays(item, userId, startTime, group.getEndTime());
            taskItemDayDao.saveAll(taskItemDays);
        });

        // 添加成员信息
        TaskMemberEntity member = buildTaskMember(userId, groupId);
        TaskMemberEntity saveMember = taskMemberDao.save(member);
        // 添加任务内每日成员信息， 若任务未开始，从任务开始日起添加，若任务已经开始，从当前天添加
        List<TaskMemberDayEntity> members = buildTaskMemberDays(saveMember,
                startTime,
                group.getEndTime());
        taskMemberDayDao.saveAll(members);

        return null;
    }

    @Override
    public void score(Integer fromUserId, Integer toUserId, Integer groupId, Integer score)
            throws NoSuchTaskMemberDay {
        TaskMemberDayEntity memberDay = taskMemberDayDao.getByGroupIdAndUserIdAndCurrentDay(groupId, toUserId,
                DatetimeUtil.getTodayNoonTimestamp());
        // 找不到这个人在当天的任务信息
        if (memberDay == null) throw new NoSuchTaskMemberDay(String.format("No Member %d in This Group %d on %s",
                toUserId, groupId, DatetimeUtil.getTodayNoonTimestamp().toString()));

        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setMemberDayId(memberDay.getId());
        scoreEntity.setFromUserId(fromUserId);
        scoreEntity.setToUserId(toUserId);
        scoreEntity.setScore(score);

        scoreDao.save(scoreEntity);
    }

    @Override
    public List<OptionDto<String, Object>> getGroupCurrentDetail(Integer groupId, Integer userId)
            throws NoSuchMemberInGroup {
        GroupDetailSummary summary = taskGroupDao.getSummaryById(groupId);
        List<GroupDetailItem> finished = taskItemDayDao.getContentByGroupIdAndUserIdAndCurrentDayAndStatus(
                groupId,
                userId,
                DatetimeUtil.getTodayNoonTimestamp(),
                TaskFinishStatusEnum.FINISHED.getValue()
        );
        List<GroupDetailItem> unfinished = taskItemDayDao.getContentByGroupIdAndUserIdAndCurrentDayAndStatus(
                groupId,
                userId,
                DatetimeUtil.getTodayNoonTimestamp(),
                TaskFinishStatusEnum.UNFINISHED.getValue()
        );

        if (finished.size() == 0 && unfinished.size() == 0) {
            throw new NoSuchMemberInGroup(String.format("No User %d in this Group %d", userId, groupId));
        }

        List<OptionDto<String, Object>> res = new ArrayList<>();
        res.add(new OptionDto<>("summary", summary));
        res.add(new OptionDto<>("unfinished", unfinished));
        res.add(new OptionDto<>("finished", finished));

        if (summary.getType().equals(TaskGroupTypeEnum.PEOPLE.getValue())) {
            List<GroupDetailMember> members = taskMemberDao.findAllMemberOfGroup(groupId);
            res.add(new OptionDto<>("members", members));
        }
        return res;
    }

    @Override
    public List<OptionDto<String, Object>> getGroupSharedDetail(Integer groupId) {
        GroupDetailSummary summary = taskGroupDao.getSummaryById(groupId);
        List<GroupDetailItem> items = taskItemDao.findBriefByGroupId(groupId);

        List<OptionDto<String, Object>> res = new ArrayList<>();
        res.add(new OptionDto<>("summary", summary));
        res.add(new OptionDto<>("items", items));


        if (summary.getType().equals(TaskGroupTypeEnum.PEOPLE.getValue())) {
            List<GroupDetailMember> members = taskMemberDao.findAllMemberOfGroup(groupId);
            res.add(new OptionDto<>("members", members));
        }
        return res;
    }

    @Override
    public List<NotStartedTaskListProjection> getNotStartedList(Integer userId) {
        return taskDao.findNotStartedTaskList(userId, DatetimeUtil.getNowTimestamp());
    }

    private List<TaskMemberDayEntity> buildTaskMemberDays(TaskMemberEntity saveMember, Timestamp startTime, Timestamp endTime) {
        List<TaskMemberDayEntity> taskMemberDays = new ArrayList<>();

        Timestamp endDay = DatetimeUtil.getNoonTimestamp(endTime);
        Timestamp curDay = DatetimeUtil.getNoonTimestamp(startTime);
        while (curDay.compareTo(endDay) <= 0) {
            TaskMemberDayEntity memberDay = new TaskMemberDayEntity();
            memberDay.setGroupId(saveMember.getGroupId());
            memberDay.setFinishedTask(0);
            memberDay.setUserId(saveMember.getUserId());
            memberDay.setCurrentDay(curDay);
            taskMemberDays.add(memberDay);

            Timestamp nextDay =  DatetimeUtil.getNextNoonTimestamp(curDay);
            curDay = DatetimeUtil.getNoonTimestamp(nextDay);
        }
        return taskMemberDays;
    }

    private TaskMemberEntity buildTaskMember(Integer userId, Integer groupId) {
        TaskMemberEntity member = new TaskMemberEntity();
        member.setGroupId(groupId);
        member.setUserId(userId);
        return member;
    }

    /**
     * 生成任务所有天的列表
     */
    private List<TaskItemDayEntity> buildTaskItemDays(TaskItemEntity saveItem,
                                                      Integer userId,
                                                      Timestamp startTime,
                                                      Timestamp endTime) {
        List<TaskItemDayEntity> taskItemDays = new ArrayList<>();

        Timestamp endDay = DatetimeUtil.getNoonTimestamp(endTime);
        Timestamp curDay = DatetimeUtil.getNoonTimestamp(startTime);
        while (curDay.compareTo(endDay) <= 0) {
            TaskItemDayEntity itemDay = buildTaskItemDay(saveItem, userId);
            itemDay.setCurrentDay(curDay);
            taskItemDays.add(itemDay);

            Timestamp nextDay =  DatetimeUtil.getNextNoonTimestamp(curDay);
            curDay = DatetimeUtil.getNoonTimestamp(nextDay);
        }
        return taskItemDays;
    }

    /**
     * 生成itemDay
     */
    private TaskItemDayEntity buildTaskItemDay(TaskItemEntity saveItem, Integer userId) {
        TaskItemDayEntity itemDay = new TaskItemDayEntity();
        itemDay.setItemId(saveItem.getId());
        itemDay.setStatus(TaskFinishStatusEnum.UNFINISHED.getValue());
        itemDay.setUserId(userId);
        return itemDay;
    }

    /**
     * 生成item
     */
    private TaskItemEntity buildTaskItem(JSONObject item, Integer groupId) {
        TaskItemEntity taskItem = new TaskItemEntity();
        taskItem.setContent(item.getString("content"));
        taskItem.setGroupId(groupId);
        return taskItem;
    }

    /**
     * 生成group
     */
    private TaskGroupEntity buildTaskGroup(JSONObject jsonObject, Integer userId, Integer totalTask) {
        TaskGroupEntity group = new TaskGroupEntity();
        group.setTitle(jsonObject.getString("title"));
        group.setType(jsonObject.getInt("type"));
        group.setSponsorId(userId);
        group.setStartTime(DatetimeUtil.long2Timestamp(jsonObject.getLong("startTime")));
        group.setEndTime(DatetimeUtil.long2Timestamp(jsonObject.getLong("endTime")));
        group.setIsPublic(jsonObject.getBoolean("isPublic"));
        group.setMaxPeople(jsonObject.getInt("maxPeople"));
        group.setTotalTask(totalTask);
        group.setTotalPeople(1);
        return group;
    }

}
