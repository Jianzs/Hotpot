package cn.yzh.hotpot.web.controller;

import cn.yzh.hotpot.pojo.dto.ResponseDto;
import cn.yzh.hotpot.service.FormIdService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/formId")
public class FormIdController {
    private FormIdService formIdService;

    @Autowired
    public FormIdController(FormIdService formIdService) {
        this.formIdService = formIdService;
    }

    @PostMapping("")
    public ResponseDto uploadFormId(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.isNull("groupId") ||
                jsonObject.isNull("formId")) {
            return ResponseDto.failed("Group Id Or Form Id Can't Be Null.");
        }

        formIdService.uploadFormId(jsonObject.getInt("groupId"), jsonObject.getString("formId"));
        return ResponseDto.succeed();
    }
}
