package pl.appcoders.moxacontroller.status;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.appcoders.moxacontroller.R;

/**
 * Created by mkowalik on 26.02.18.
 */

class StatusContainer {
    private final Context context;

    private boolean isConnected;

    private String modelName;

    private String deviceUptime;

    private String firmwareVersion;

    private String macAddress;

    private String ipAddress;

    public StatusContainer(Context context) {
        this.context = context;
        isConnected = false;
    }

    public void setConnectionStatus(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setDeviceUptime(String deviceUptime) {
        this.deviceUptime = deviceUptime;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<StatusItem> getStatusItemList() {
        List<StatusItem> list = new ArrayList<>();
        list.add(createMainStatusItem());
        list.add(createStatusItem(R.string.model_name, modelName));
        list.add(createStatusItem(R.string.device_uptime, deviceUptime));
        list.add(createStatusItem(R.string.firmware_version, firmwareVersion));
        list.add(createStatusItem(R.string.mac_address, macAddress));
        list.add(createStatusItem(R.string.ip_address, ipAddress));
        return list;
    }

    private StatusItem createStatusItem(int id, String data) {
        return new StatusItem(context.getString(id), isConnected ? data :
                context.getString(R.string.no_data));
    }

    private StatusItem createMainStatusItem() {
        return new StatusItem(context.getString(R.string.connection_status),
                isConnected ? context.getString(R.string.online_status) :
                        context.getString(R.string.offline_status));
    }
}
