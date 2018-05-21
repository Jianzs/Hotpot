package cn.yzh.hotpot.enums;

public enum UserGenderEnum {
    MALE(1),
    FEMALE(0);

    private Integer value;

    UserGenderEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
