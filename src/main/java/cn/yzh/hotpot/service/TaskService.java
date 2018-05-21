package cn.yzh.hotpot.service;

import cn.yzh.hotpot.dao.projection.HistoryTaskListProjection;
import cn.yzh.hotpot.dao.projection.PendingTaskListProjection;
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

    void createTaskGroup(JSONObject jsonObject, Integer userId);
}
