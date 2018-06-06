package cn.yzh.hotpot.enums;

public enum FormIdStatusEnum {
    UNUSED(0),
    USED(1);

    private Integer value;

    FormIdStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
