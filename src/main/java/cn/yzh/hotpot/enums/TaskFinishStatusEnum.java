package cn.yzh.hotpot.enums;

public enum TaskFinishStatusEnum {
    UNFINISHED(0),
    FINISHED(1);

    private Integer value;

    TaskFinishStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
