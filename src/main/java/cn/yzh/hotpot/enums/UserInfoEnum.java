package cn.yzh.hotpot.enums;

public enum UserInfoEnum {
    UNCOMPLETED(0),
    COMPLETED(1);

    private Integer value;

    UserInfoEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
