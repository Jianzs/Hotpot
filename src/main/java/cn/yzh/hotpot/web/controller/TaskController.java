package cn.yzh.hotpot.web.controller;

import cn.yzh.hotpot.dao.projection.HistoryTaskListProjection;
import cn.yzh.hotpot.dao.projection.PendingTaskListProjection;
import cn.yzh.hotpot.pojo.dto.ResponseDto;
import cn.yzh.hotpot.service.TaskService;
import cn.yzh.hotpot.util.JWTUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Value("${task.perPageNum}")
    private Integer PER_PAGE_NUM;
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 当前任务
     */
    @GetMapping("/pending")
    public ResponseDto getPendingTaskList(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        List<PendingTaskListProjection> taskList = taskService.getPendingTaskList(userId);
        return ResponseDto.succeed().setData("groups", taskList);
    }

    /**
     * 历史任务
     */
    @GetMapping("/history/{pageNum}")
    public ResponseDto getHistoryTaskList(@PathVariable("pageNum")Integer pageNum,
                                          HttpServletRequest request) {
        if (pageNum <= 0) return ResponseDto.failed("Page Number Can't Less Than 1.");
        pageNum--;

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);

        Page<HistoryTaskListProjection> taskList = taskService.getHistoryTaskList(userId, PageRequest.of(
                pageNum,
                PER_PAGE_NUM,
                Sort.Direction.DESC,
                "I.end_time"
        ));

        return ResponseDto.succeed()
                .setData("groups", taskList.getContent())
                .setData("pageSum", taskList.getTotalPages());
    }

    /**
     * 新建任务
     */
    @PostMapping("/group")
    public ResponseDto createTaskGroup(@RequestBody String json, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("title") ||
                jsonObject.isNull("type") ||
                jsonObject.isNull("startTime")||
                jsonObject.isNull("endTime") ||
                jsonObject.isNull("maxPeople") ||
                jsonObject.isNull("isPublic") ||
                jsonObject.getJSONArray("items").length() <= 0) {
            return ResponseDto.failed("Something Is Blank.");
        }

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        taskService.createTaskGroup(jsonObject, userId);

        return ResponseDto.succeed();
    }
}
