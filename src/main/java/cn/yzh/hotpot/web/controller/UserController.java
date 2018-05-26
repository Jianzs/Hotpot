package cn.yzh.hotpot.web.controller;

import cn.yzh.hotpot.dao.projection.PersonScoreProjection;
import cn.yzh.hotpot.dao.projection.ScoreHistoryProjection;
import cn.yzh.hotpot.dao.projection.UserInfoProjection;
import cn.yzh.hotpot.dao.projection.UserRankProjection;
import cn.yzh.hotpot.exception.ConnectWechatException;
import cn.yzh.hotpot.pojo.dto.OptionDto;
import cn.yzh.hotpot.pojo.dto.ResponseDto;
import cn.yzh.hotpot.pojo.entity.UserEntity;
import cn.yzh.hotpot.service.UserService;
import cn.yzh.hotpot.util.JWTUtil;
import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${user.rank.perPageNum}")
    private Integer PER_PAGE_RANK;
    @Value("${user.login.wechatCode}")
    private String WECHAR_CODE;
    @Value("${user.score.history.perPageNum}")
    private Integer PER_PAGE_SCORE_HISTORY;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody String json)
            throws UnsupportedEncodingException, ConnectWechatException {
        JSONObject jsonObject = new JSONObject(json);
        String code = jsonObject.getString(WECHAR_CODE);

        OptionDto<Boolean, String> token = userService.login(code);
        if (token != null) {
            return ResponseDto.succeed()
                    .setData("isNew", token.getOptKey())
                    .setData("token", token.getOptVal());
        } else {
            return ResponseDto.failed("Code is wrong.");
        }
    }

    /**
     * 获取用户个人信息
     */
    @GetMapping("/info")
    public ResponseDto getUserInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        UserInfoProjection userInfo = userService.getUserInfo(userId);
        return ResponseDto.succeed().setData("info", userInfo);
    }
    /**
     * 用户上传信息
     */
    @PutMapping("/info")
    public ResponseDto updateInfo(@RequestBody UserEntity user, HttpServletRequest request) {
        if (user.getAvatar() == null ||
                user.getBirthday() == null ||
                user.getCollage() == null ||
                user.getGender() == null ||
                user.getGrade() == null) {
            return ResponseDto.failed("Something is Blank.");
        }

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);

        user.setId(userId);
        userService.updateInfo(user);

        return ResponseDto.succeed();
    }

    /**
     * 个人中心-个人积分
     */
    @GetMapping("/score")
    public ResponseDto getScore(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        PersonScoreProjection personScore = userService.getScore(userId);
        return ResponseDto.succeed().setData("score", personScore);
    }

    /**
     * 排行榜
     */
    @GetMapping("/rank/{pageNum}")
    public ResponseDto getRank(@PathVariable("pageNum") Integer pageNum) {
        if (pageNum <= 0) {
            return ResponseDto.failed("Page Number is Wrong.");
        }
        pageNum--;

        Page<UserRankProjection> userRank = userService.getRank(PageRequest.of(
                pageNum,
                PER_PAGE_RANK,
                Sort.Direction.DESC,
                "people_score"
        ));
        return ResponseDto.succeed()
                .setData("rank", userRank.getContent())
                .setData("pageSum", userRank.getTotalPages());
    }

    /**
     * 积分记录
     */
    @GetMapping("/score/history/{pageNum}")
    public ResponseDto getScoreHistory(@PathVariable("pageNum") Integer pageNum,
                                       HttpServletRequest request) {
        if (pageNum <= 0) {
            return ResponseDto.failed("Page Number is Wrong.");
        }
        pageNum--;

        Integer userId = (Integer) request.getAttribute(JWTUtil.USER_ID_KEY);
        Page<ScoreHistoryProjection> scoreHistory = userService.getScoreHistory(userId,
                PageRequest.of(pageNum,
                        PER_PAGE_SCORE_HISTORY,
                        Sort.Direction.DESC,
                        "current_day"));
        return ResponseDto.succeed()
                .setData("scores", scoreHistory.getContent())
                .setData("pageSum", scoreHistory.getTotalPages());
    }
}
