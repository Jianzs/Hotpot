package cn.yzh.hotpot.web.controller;

import cn.yzh.hotpot.dao.projection.HistoryTaskListProjection;
import cn.yzh.hotpot.dao.projection.NotStartedTaskListProjection;
import cn.yzh.hotpot.dao.projection.PendingTaskListProjection;
import cn.yzh.hotpot.exception.NoSuchMemberInGroup;
import cn.yzh.hotpot.exception.NoSuchTaskMemberDay;
import cn.yzh.hotpot.pojo.dto.OptionDto;
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
     * 新建任务组
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
        Integer groupId = taskService.createTaskGroup(jsonObject, userId);

        return ResponseDto.succeed()
                .setData("groupId", groupId);
    }

    /**
     * 获得用户当前任务详情
     */
    @GetMapping({"/group/{groupId}", "/group/{groupId}/{userId}"})
    public ResponseDto getGroupDetail(@PathVariable("groupId") Integer groupId,
                                      @PathVariable(value = "userId", required = false) Integer userId,
                                      HttpServletRequest request)
            throws NoSuchMemberInGroup {
        if (userId == null)
            userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        List<OptionDto<String, Object>> res = taskService.getGroupCurrentDetail(groupId, userId);

        ResponseDto responseDto = ResponseDto.succeed();
        res.forEach((item) -> responseDto.setData(item.getOptKey(), item.getOptVal()));
        return responseDto;
    }

    /**
     * 加入任务组
     */
    @PostMapping("/group/join")
    public ResponseDto joinTaskGroup(@RequestBody String json, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("groupId")) {
            return ResponseDto.failed("Group Id is Blank.");
        }

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        OptionDto<Integer, String> joinStatus = taskService.joinTaskGroup(jsonObject, userId);
        if (joinStatus != null) {
            return ResponseDto.failed()
                    .setMessage(joinStatus.getOptVal())
                    .setData("code", joinStatus.getOptKey());
        }
        return ResponseDto.succeed();
    }

    /**
     * 完成任务项
     */
    @PostMapping("/item")
    public ResponseDto finishTaskItem(@RequestBody String json, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("itemId") ||
                jsonObject.isNull("groupId")) {
            return ResponseDto.failed("Something is Blank.");
        }

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        OptionDto<Integer, String> finishStatus = taskService.finishTaskItem(jsonObject, userId);
        if (finishStatus != null) {
            return ResponseDto.failed()
                    .setMessage(finishStatus.getOptVal())
                    .setData("code", finishStatus.getOptKey());
        }
        return ResponseDto.succeed();
    }

    /**
     * 打分
     */
    @PostMapping("/score")
    public ResponseDto score(@RequestBody String json, HttpServletRequest request)
            throws NoSuchTaskMemberDay {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("toUserId") ||
                jsonObject.isNull("groupId") ||
                jsonObject.isNull("score")) {
            return ResponseDto.failed("Something is Blank.");
        }

        Integer fromUserId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        Integer toUserId = jsonObject.getInt("toUserId");
        Integer groupId = jsonObject.getInt("groupId");
        Integer score = jsonObject.getInt("score");

        taskService.score(fromUserId, toUserId, groupId, score);

        return ResponseDto.succeed();
    }

    /**
     * 未开始任务
     */
    @GetMapping("/not-start")
    public ResponseDto getNotStarted(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);

        List<NotStartedTaskListProjection> list = taskService.getNotStartedList(userId);

        return ResponseDto.succeed().setData("tasks", list);
    }

    /**
     * 分享时，任务详情
     */
    @GetMapping("/detail/simple/{id}")
    public ResponseDto getTaskGroupSharedDetail(@PathVariable("id") Integer groupId) {
        List<OptionDto<String, Object>> res = taskService.getGroupSharedDetail(groupId);

        ResponseDto responseDto = ResponseDto.succeed();
        res.forEach((item) -> responseDto.setData(item.getOptKey(), item.getOptVal()));
        return responseDto;
    }
}
