package cn.yzh.hotpot.exception.handler;

import cn.yzh.hotpot.exception.NoAuthenticationException;
import cn.yzh.hotpot.exception.NoAuthorizationException;
import cn.yzh.hotpot.pojo.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class DefaultExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseDto defaultExceptionHandler(HttpServletResponse response, Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(e.fillInStackTrace()).append("\n");

        for (StackTraceElement element : stackTrace) {
            sb.append("\tat ").append(element).append("\n");
        }

        ResponseDto responseDto = ResponseDto.failed();
        if (e instanceof NoAuthenticationException) {
            logger.info(sb.toString());
            responseDto.setMessage(e.getMessage());
        } else if (e instanceof NoAuthorizationException){
            logger.info(sb.toString());
            responseDto.setMessage(e.getMessage());
        } else {
            logger.error(sb.toString());
            responseDto.setMessage("Internet Server Error.");
        }
        return responseDto;
    }
}
