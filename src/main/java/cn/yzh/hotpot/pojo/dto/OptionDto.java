package cn.yzh.hotpot.pojo.dto;

public class OptionDto<T1, T2> {
    private T1 optKey;
    private T2 optVal;

    public OptionDto(T1 optKey, T2 optVal) {
        this.optKey = optKey;
        this.optVal = optVal;
    }

    public T1 getOptKey() {
        return optKey;
    }

    public void setOptKey(T1 optKey) {
        this.optKey = optKey;
    }

    public T2 getOptVal() {
        return optVal;
    }

    public void setOptVal(T2 optVal) {
        this.optVal = optVal;
    }
}
