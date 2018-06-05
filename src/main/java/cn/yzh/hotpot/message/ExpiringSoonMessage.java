package cn.yzh.hotpot.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpiringSoonMessage {
    private static String template = "{\n" +
            "  \"touser\": \"%s\",\n" +
            "  \"template_id\": \"qiLXz5vkVSdDcnhTgQfH1PodH-zcRw3uhcR_q2JFSY4\",\n" +
            "  \"page\": \"/pages/task/task?groupId=%d&from=message\",\n" +
            "  \"form_id\": \"%s\",\n" +
            "  \"data\": {\n" +
            "      \"keyword1\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      },\n" +
            "      \"keyword2\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      },\n" +
            "      \"keyword3\": {\n" +
            "          \"value\": \"%d\"\n" +
            "      } ,\n" +
            "      \"keyword4\": {\n" +
            "          \"value\": \"%s\"\n" +
            "      }\n" +
            "  },\n" +
            "  \"emphasis_keyword\": \"keyword1.DATA\"\n" +
            "}";

    public static String createMessage(String openId, Integer groupId, String formId,
                                       String title, Date date, Integer totalPeople,
                                       String items) {
        String dateFormat = "yyyy年MM月dd日 HH:mm";
        String formatDate = new SimpleDateFormat(dateFormat).format(date);
        return String.format(template, openId, groupId, formId, title, formatDate, totalPeople, items);
    }
}
