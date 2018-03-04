
package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SysInfo {

    @SerializedName("device")
    @Expose
    private List<Device> device = new ArrayList<Device>();
    @SerializedName("network")
    @Expose
    private Network network;

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

}
