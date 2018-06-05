package cn.yzh.hotpot.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinSuccMessage {
    private static String template = "{\n" +
            "  \"touser\": \"%s\",\n" +
            "  \"template_id\": \"ENH-oU8L8mWvhvL2P7J8JmbWcIN-iu3bwXJmjE4iyfI\",\n" +
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
            "          \"value\": \"%s\"\n" +
            "      } \n" +
            "  },\n" +
            "  \"emphasis_keyword\": \"keyword1.DATA\"\n" +
            "}";

    public static String createMessage(String openId, Integer groupId, String formId,
                                       String title, Date date, String items) {
        String dateFormat = "yyyy年MM月dd日 HH:mm";
        String formatDate = new SimpleDateFormat(dateFormat).format(date);
        return String.format(template, openId, groupId, formId, title, formatDate, items);
    }
}
