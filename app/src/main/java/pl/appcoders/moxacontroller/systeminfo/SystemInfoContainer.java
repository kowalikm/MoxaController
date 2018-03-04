package pl.appcoders.moxacontroller.systeminfo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.systeminfo.dto.Device;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;

class SystemInfoContainer {
    private final Context context;

    private boolean isConnected;

    private String modelName;

    private String deviceName;

    private String deviceUptime;

    private String firmwareVersion;

    private String macAddress;

    private String ipAddress;

    SystemInfoContainer(Context context) {
        this.context = context;
        isConnected = false;
    }

    SystemInfoContainer(SystemInfo systemInfo, Context context) {
        this.context = context;
        isConnected = true;
        Device device = systemInfo.getSysInfo().getDevice().get(0);
        modelName = device.getModelName();
        deviceName = device.getDeviceName();
        deviceUptime = device.getDeviceUpTime();
        firmwareVersion = device.getFirmwareVersion();
        macAddress = systemInfo.getSysInfo().getNetwork().getLAN().getLanMac();
        ipAddress = systemInfo.getSysInfo().getNetwork().getLAN().getLanIp();
    }

    List<SystemInfoItem> getSystemInfoItemList() {
        List<SystemInfoItem> list = new ArrayList<>();
        list.add(createMainSystemInfoItem());
        list.add(createSystemInfoItem(R.string.model_name, modelName));
        list.add(createSystemInfoItem(R.string.device_name, deviceName));
        list.add(createSystemInfoItem(R.string.device_uptime, deviceUptime));
        list.add(createSystemInfoItem(R.string.firmware_version, firmwareVersion));
        list.add(createSystemInfoItem(R.string.mac_address, macAddress));
        list.add(createSystemInfoItem(R.string.ip_address, ipAddress));
        return list;
    }

    private SystemInfoItem createSystemInfoItem(int id, String data) {
        return new SystemInfoItem(context.getString(id), isConnected ? data :
                context.getString(R.string.no_data));
    }

    private SystemInfoItem createMainSystemInfoItem() {
        return new SystemInfoItem(context.getString(R.string.connection_status),
                isConnected ? context.getString(R.string.online_status) :
                        context.getString(R.string.offline_status));
    }
}
