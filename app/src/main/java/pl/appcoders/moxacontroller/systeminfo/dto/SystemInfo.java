package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemInfo {
    @SerializedName("device")
    @Expose
    private DeviceSystemInfo device;

    @SerializedName("network")
    @Expose
    private NetworkSystemInfo network;

    public DeviceSystemInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceSystemInfo device) {
        this.device = device;
    }

    public NetworkSystemInfo getNetwork() {
        return network;
    }

    public void setNetwork(NetworkSystemInfo network) {
        this.network = network;
    }
}