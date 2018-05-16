
package pl.appcoders.moxacontroller.relays.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relay {

    @SerializedName("relayIndex")
    @Expose
    private Integer relayIndex;
    @SerializedName("relayMode")
    @Expose
    private Integer relayMode;
    @SerializedName("relayPulseStatus")
    @Expose
    private Integer relayPulseStatus;
    @SerializedName("relayPulseCount")
    @Expose
    private Integer relayPulseCount;
    @SerializedName("relayPulseOnWidth")
    @Expose
    private Integer relayPulseOnWidth;
    @SerializedName("relayPulseOffWidth")
    @Expose
    private Integer relayPulseOffWidth;
    @SerializedName("relayTotalCount")
    @Expose
    private Long relayTotalCount;
    @SerializedName("relayCurrentCount")
    @Expose
    private Integer relayCurrentCount;
    @SerializedName("relayCurrentCountReset")
    @Expose
    private Integer relayCurrentCountReset;
    @SerializedName("relayStatus")
    @Expose
    private Integer relayStatus;

    public Integer getRelayIndex() {
        return relayIndex;
    }

    public void setRelayIndex(Integer relayIndex) {
        this.relayIndex = relayIndex;
    }

    public Integer getRelayMode() {
        return relayMode;
    }

    public void setRelayMode(Integer relayMode) {
        this.relayMode = relayMode;
    }

    public Integer getRelayPulseStatus() {
        return relayPulseStatus;
    }

    public void setRelayPulseStatus(Integer relayPulseStatus) {
        this.relayPulseStatus = relayPulseStatus;
    }

    public Integer getRelayPulseCount() {
        return relayPulseCount;
    }

    public void setRelayPulseCount(Integer relayPulseCount) {
        this.relayPulseCount = relayPulseCount;
    }

    public Integer getRelayPulseOnWidth() {
        return relayPulseOnWidth;
    }

    public void setRelayPulseOnWidth(Integer relayPulseOnWidth) {
        this.relayPulseOnWidth = relayPulseOnWidth;
    }

    public Integer getRelayPulseOffWidth() {
        return relayPulseOffWidth;
    }

    public void setRelayPulseOffWidth(Integer relayPulseOffWidth) {
        this.relayPulseOffWidth = relayPulseOffWidth;
    }

    public Long getRelayTotalCount() {
        return relayTotalCount;
    }

    public void setRelayTotalCount(Long relayTotalCount) {
        this.relayTotalCount = relayTotalCount;
    }

    public Integer getRelayCurrentCount() {
        return relayCurrentCount;
    }

    public void setRelayCurrentCount(Integer relayCurrentCount) {
        this.relayCurrentCount = relayCurrentCount;
    }

    public Integer getRelayCurrentCountReset() {
        return relayCurrentCountReset;
    }

    public void setRelayCurrentCountReset(Integer relayCurrentCountReset) {
        this.relayCurrentCountReset = relayCurrentCountReset;
    }

    public Integer getRelayStatus() {
        return relayStatus;
    }

    public void setRelayStatus(Integer relayStatus) {
        this.relayStatus = relayStatus;
    }

    @Override
    public String toString() {
        return "/io/relay/" + getRelayIndex();
    }
}
