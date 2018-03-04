
package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemInfo {

    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("sysInfo")
    @Expose
    private SysInfo sysInfo;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public SysInfo getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(SysInfo sysInfo) {
        this.sysInfo = sysInfo;
    }

}
