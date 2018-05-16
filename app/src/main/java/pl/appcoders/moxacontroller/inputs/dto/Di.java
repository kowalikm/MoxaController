package pl.appcoders.moxacontroller.inputs.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Di {

    @SerializedName("diIndex")
    @Expose
    private Integer diIndex;
    @SerializedName("diMode")
    @Expose
    private Integer diMode;
    @SerializedName("diCounterValue")
    @Expose
    private Integer diCounterValue;
    @SerializedName("diCounterStatus")
    @Expose
    private Integer diCounterStatus;
    @SerializedName("diCounterReset")
    @Expose
    private Integer diCounterReset;
    @SerializedName("diCounterOverflowFlag")
    @Expose
    private Integer diCounterOverflowFlag;
    @SerializedName("diCounterOverflowClear")
    @Expose
    private Integer diCounterOverflowClear;
    @SerializedName("diStatus")
    @Expose
    private Integer diStatus;

    public Integer getDiIndex() {
        return diIndex;
    }

    public void setDiIndex(Integer diIndex) {
        this.diIndex = diIndex;
    }

    public Integer getDiMode() {
        return diMode;
    }

    public void setDiMode(Integer diMode) {
        this.diMode = diMode;
    }

    public Integer getDiCounterValue() {
        return diCounterValue;
    }

    public void setDiCounterValue(Integer diCounterValue) {
        this.diCounterValue = diCounterValue;
    }

    public Integer getDiCounterStatus() {
        return diCounterStatus;
    }

    public void setDiCounterStatus(Integer diCounterStatus) {
        this.diCounterStatus = diCounterStatus;
    }

    public Integer getDiCounterReset() {
        return diCounterReset;
    }

    public void setDiCounterReset(Integer diCounterReset) {
        this.diCounterReset = diCounterReset;
    }

    public Integer getDiCounterOverflowFlag() {
        return diCounterOverflowFlag;
    }

    public void setDiCounterOverflowFlag(Integer diCounterOverflowFlag) {
        this.diCounterOverflowFlag = diCounterOverflowFlag;
    }

    public Integer getDiCounterOverflowClear() {
        return diCounterOverflowClear;
    }

    public void setDiCounterOverflowClear(Integer diCounterOverflowClear) {
        this.diCounterOverflowClear = diCounterOverflowClear;
    }

    public Integer getDiStatus() {
        return diStatus;
    }

    public void setDiStatus(Integer diStatus) {
        this.diStatus = diStatus;
    }

    @Override
    public String toString() {
        return "/io/di/" + getDiIndex();
    }

}