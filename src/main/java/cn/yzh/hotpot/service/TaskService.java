package cn.yzh.hotpot.service;

import cn.yzh.hotpot.dao.projection.HistoryTaskListProjection;
import cn.yzh.hotpot.dao.projection.NotStartedTaskListProjection;
import cn.yzh.hotpot.dao.projection.PendingTaskListProjection;
import cn.yzh.hotpot.exception.NoSuchMemberInGroup;
import cn.yzh.hotpot.exception.NoSuchTaskMemberDay;
import cn.yzh.hotpot.pojo.dto.OptionDto;
import cn.yzh.hotpot.pojo.dto.VillageItemDto;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    /**
     * 获得个人主页项目列表
     */
    List<PendingTaskListProjection> getPendingTaskList(Integer userId);

    Page<HistoryTaskListProjection> getHistoryTaskList(Integer userId, Pageable pageable);

    Integer createTaskGroup(JSONObject jsonObject, Integer userId);

    OptionDto<Integer, String> finishTaskItem(JSONObject jsonObject, Integer userId);

    OptionDto<Integer, String> joinTaskGroup(JSONObject jsonObject, Integer userId);

    void score(Integer fromUserId, Integer toUserId, Integer groupId, Integer score) throws NoSuchTaskMemberDay;

    List<OptionDto<String, Object>> getGroupCurrentDetail(Integer groupId, Integer userId) throws NoSuchMemberInGroup;

    List<OptionDto<String,Object>> getGroupSharedDetail(Integer groupId);

    List<NotStartedTaskListProjection> getNotStartedList(Integer userId);

    List<VillageItemDto> getTaskVillage(Integer limit);
}
