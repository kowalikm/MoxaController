package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkSystemInfo {
    @SerializedName("LAN")
    @Expose
    private LanNetworkSystemInfo lanNetworkSystemInfo;

    public LanNetworkSystemInfo getLanNetworkSystemInfo() {
        return lanNetworkSystemInfo;
    }

    public void setLanNetworkSystemInfo(LanNetworkSystemInfo lanNetworkSystemInfo) {
        this.lanNetworkSystemInfo = lanNetworkSystemInfo;
    }
}
