package cn.yzh.hotpot.enums;

public enum UserRoleEnum {
    ORDINARY_USER(0),
    ADMINISTRATION(1);

    private Integer value;

    UserRoleEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
