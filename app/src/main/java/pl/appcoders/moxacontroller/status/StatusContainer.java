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

    private String deviceName;

    private String deviceUptime;

    private String firmwareVersion;

    private String macAddress;

    private String ipAddress;

    public StatusContainer(Context context) {
        this.context = context;
        isConnected = false;
    }

    public StatusContainer(SystemInfo systemInfo, Context context) {
        this.context = context;
        isConnected = true;
        modelName = systemInfo.getDevice().getModelName();
        deviceName = systemInfo.getDevice().getDeviceName();
        deviceUptime = systemInfo.getDevice().getDeviceUpTime();
        firmwareVersion = systemInfo.getDevice().getFirmwareVersion();
        macAddress = systemInfo.getNetwork().getLanNetworkSystemInfo().getLanMac();
        ipAddress = systemInfo.getNetwork().getLanNetworkSystemInfo().getLanIp();
    }

    public List<StatusItem> getStatusItemList() {
        List<StatusItem> list = new ArrayList<>();
        list.add(createMainStatusItem());
        list.add(createStatusItem(R.string.model_name, modelName));
        list.add(createStatusItem(R.string.device_name, deviceName));
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
