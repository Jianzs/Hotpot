package cn.yzh.hotpot.enums;

public enum UploadFormIdTypeEum {
    CREATE_OR_JOIN(0),
    JUST_UPLOAD(1);

    private Integer value;

    UploadFormIdTypeEum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
