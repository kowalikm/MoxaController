package pl.appcoders.moxacontroller.systeminfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceSystemInfo {
    @SerializedName("modelName")
    @Expose
    private String modelName;

    @SerializedName("deviceName")
    @Expose
    private String deviceName;

    @SerializedName("deviceUpTime")
    @Expose
    private String deviceUpTime;

    @SerializedName("firmwareVersion")
    @Expose
    private String firmwareVersion;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUpTime() {
        return deviceUpTime;
    }

    public void setDeviceUpTime(String deviceUpTime) {
        this.deviceUpTime = deviceUpTime;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
}
