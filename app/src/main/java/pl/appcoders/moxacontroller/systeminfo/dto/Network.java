
package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network {

    @SerializedName("LAN")
    @Expose
    private LAN lan;

    public LAN getLAN() {
        return lan;
    }

    public void setLAN(LAN lan) {
        this.lan = lan;
    }

}
