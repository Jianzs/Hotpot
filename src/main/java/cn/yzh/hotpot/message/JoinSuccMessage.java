package cn.yzh.hotpot.message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinSuccMessage {
    private static String template = "{" +
            "  \"touser\": \"%s\"," +
            "  \"template_id\": \"ENH-oU8L8mWvhvL2P7J8JmbWcIN-iu3bwXJmjE4iyfI\"," +
            "  \"page\": \"/pages/task/task?groupId=%d&from=message\"," +
            "  \"form_id\": \"%s\"," +
            "  \"data\": {" +
            "      \"keyword1\": {" +
            "          \"value\": \"%s\"" +
            "      }," +
            "      \"keyword2\": {" +
            "          \"value\": \"%s\"" +
            "      },\n" +
            "      \"keyword3\": {\n" +
            "          \"value\": \"%s\"" +
            "      }" +
            "  },\n" +
            "  \"emphasis_keyword\": \"keyword1.DATA\"" +
            "}";

    public static String createMessage(String openId, Integer groupId, String formId,
                                       String title, Date date, String items) {
        String dateFormat = "yyyy年MM月dd日 HH:mm";
        String formatDate = new SimpleDateFormat(dateFormat).format(date);
        return String.format(template, openId, groupId, formId, title, formatDate, items);
    }
}
