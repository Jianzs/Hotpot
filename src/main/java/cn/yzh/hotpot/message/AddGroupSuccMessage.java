package cn.yzh.hotpot.message;

import cn.yzh.hotpot.dao.projection.GroupDetailItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddGroupSuccMessage {
    private static String template = "{\n" +
            "  \"touser\": \"%s\",\n" +
            "  \"template_id\": \"6EjnjszE9vjjpOwIdtogU2uif0lcGW6v_qKnm07yXas\",\n" +
            "  \"page\": \"pages/task/task?groupId=%d&from=message\",\n" +
            "  \"form_id\": \"%s\",\n" +
            "  \"data\": {\n" +
            "      \"keyword1\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      },\n" +
            "      \"keyword2\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      },\n" +
            "      \"keyword3\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      } ,\n" +
            "      \"keyword4\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      } ,\n" +
            "      \"keyword5\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      }\n" +
            "  },\n" +
            "  \"emphasis_keyword\": \"keyword1.DATA\"\n" +
            "}";

    public static String createMessage(String openId, Integer groupId, String formId,
                                       String title, Date start, Date end, Integer totalPeople,
                                       List<GroupDetailItem> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            GroupDetailItem item = items.get(i);
            if(i != 0) sb.append("\\n");
            sb.append(i+1).append(". ").append(item.getContent());
        }

        String dateFormat = "yyyy-MM-dd HH:mm";
        String startDate = new SimpleDateFormat(dateFormat).format(start);
        String endDate = new SimpleDateFormat(dateFormat).format(end);

        return String.format(template,
                openId,
                groupId,
                formId,
                title,  // 标题
                startDate,
                endDate,
                sb.toString(),  // 计划内容
                String.format("当前任务组共有%d人", totalPeople));  //温馨提示
    }
}
