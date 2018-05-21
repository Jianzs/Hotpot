package cn.yzh.hotpot.enums;

public enum TaskGroupTypeEnum {
    PERSONAL(1),
    PEOPLE(0);

    private Integer value;


    TaskGroupTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
