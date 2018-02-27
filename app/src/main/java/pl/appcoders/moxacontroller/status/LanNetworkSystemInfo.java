package pl.appcoders.moxacontroller.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mkowalik on 26.02.18.
 */

class LanNetworkSystemInfo {
    @SerializedName("lanMac")
    @Expose
    private String lanMac;

    @SerializedName("lanIp")
    @Expose
    private String lanIp;

    public String getLanMac() {
        return lanMac;
    }

    public void setLanMac(String lanMac) {
        this.lanMac = lanMac;
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp;
    }
}
