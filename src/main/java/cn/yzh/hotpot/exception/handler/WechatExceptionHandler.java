package cn.yzh.hotpot.exception.handler;

import cn.yzh.hotpot.exception.ConnectWechatException;
import cn.yzh.hotpot.pojo.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class WechatExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(WechatExceptionHandler.class);

    @ExceptionHandler(value = {ConnectWechatException.class})
    public ResponseDto wechatErrorHandler(HttpServletResponse response, Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(e.fillInStackTrace()).append("\n");

        for (StackTraceElement element : stackTrace) {
            sb.append("\tat ").append(element).append("\n");
        }

        logger.error(sb.toString());

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseDto.failed().setMessage(e.getMessage());
    }

}
