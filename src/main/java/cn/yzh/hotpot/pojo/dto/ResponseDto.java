package cn.yzh.hotpot.pojo.dto;

import cn.yzh.hotpot.enums.ResponseStatusEnum;

import java.util.HashMap;
import java.util.Map;

public class ResponseDto {
    // 返回状态码
    private int status;
    //返回信息
    private String message;
    // 返回数据
    private Map<String, Object> data;

    private ResponseDto() {
    }

    private ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = new HashMap<>();
    }

    /**
     * 成功时，返回的对象,只有状态码
     */
    public static ResponseDto succeed() {
        return new ResponseDto(ResponseStatusEnum.SUCCEED.getValue(), "SUCCESS");
    }

    /**
     * 成功时，返回的对象,含有信息
     */
    public static ResponseDto succeed(String message) {
        return new ResponseDto(ResponseStatusEnum.SUCCEED.getValue(), message);
    }

    /**
     * 失败时，返回的对象，只含有状态码
     */
    public static ResponseDto failed() {
        return new ResponseDto(ResponseStatusEnum.FAILED.getValue(), "FAILED");
    }


    /**
     * 失败时，返回的对象，含有信息
     */
    public static ResponseDto failed(String message) {
        return new ResponseDto(ResponseStatusEnum.FAILED.getValue(), message);
    }

    public ResponseDto setStatus(int status) {
        this.status = status;
        return this;
    }

    public ResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDto setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
