package pl.appcoders.moxacontroller.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mkowalik on 26.02.18.
 */

class NetworkSystemInfo {
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
