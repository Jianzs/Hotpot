package cn.yzh.hotpot.enums;

public enum UserGradeEnum {
    COLLEGE_ONE(0),
    COLLEGE_TWO(1),
    COLLEGE_THREE(2),
    COLLEGE_FOUR(3),
    GRADUATE_ONE(4),
    GRADUATE_TWO(5),
    GRADUATE_THREE(6);

    private Integer value;

    UserGradeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
