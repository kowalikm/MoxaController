package pl.appcoders.moxacontroller.systeminfo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.Arrays;

import javax.inject.Inject;

import pl.appcoders.moxacontroller.App;
import pl.appcoders.moxacontroller.systeminfo.dto.Device;
import pl.appcoders.moxacontroller.systeminfo.dto.LAN;
import pl.appcoders.moxacontroller.systeminfo.dto.Network;
import pl.appcoders.moxacontroller.systeminfo.dto.SysInfo;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;
import pl.appcoders.moxacontroller.systeminfo.service.SystemInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mkowalik on 04.03.18.
 */

public class SystemInfoViewModel extends ViewModel {
    private MutableLiveData<String> isConnectedMutableLiveData;
    private MutableLiveData<SystemInfo> systemInfoMutableLiveData;

    @Inject
    SystemInfoService systemInfoService;

    public SystemInfoViewModel() {
        App.getInstance().getApplicationComponent().inject(this);
    }

    LiveData<SystemInfo> getSystemInfo() {
        if(systemInfoMutableLiveData == null) {
            systemInfoMutableLiveData = new MutableLiveData<>();
            setDefaultSystemInfoValues();
        }
        return systemInfoMutableLiveData;
    }

    LiveData<String> getIsConnected() {
        if(isConnectedMutableLiveData == null) {
            isConnectedMutableLiveData = new MutableLiveData<>();
            setDefaultConnectedStringValue();
        }
        return isConnectedMutableLiveData;
    }

    void refresh() {
        Log.i("Refresh", "Refreshing viewmodel");
        systemInfoService.getSystemInfo().enqueue(new Callback<SystemInfo>() {
            @Override
            public void onResponse(Call<SystemInfo> call, Response<SystemInfo> response) {
                if(response.isSuccessful()) {
                    systemInfoMutableLiveData.postValue(response.body());
                    isConnectedMutableLiveData.postValue("Online");
                } else {
                    setDefaultValues();
                    Log.i("GetSystemInfoResponse", response.message());
                    //Handle
                }
            }

            @Override
            public void onFailure(Call<SystemInfo> call, Throwable t) {
                setDefaultValues();
                Log.i("GetSystemInfoFailure", t.getMessage());
                //Handle
            }
        });
    }

    private void setDefaultValues() {
        setDefaultConnectedStringValue();
        setDefaultSystemInfoValues();
    }

    private void setDefaultConnectedStringValue() {
        isConnectedMutableLiveData.postValue("Offline");
    }

    private void setDefaultSystemInfoValues() {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setSlot(0);
        systemInfo.setSysInfo(getDefaultSysInfoValues());
        systemInfoMutableLiveData.postValue(systemInfo);
    }

    private SysInfo getDefaultSysInfoValues() {
        SysInfo sysInfo = new SysInfo();
        sysInfo.setDevice(Arrays.asList(getDefaultDeviceValues()));
        sysInfo.setNetwork(getDefaultNetworkValues());
        return sysInfo;
    }

    private Device getDefaultDeviceValues() {
        Device device = new Device();
        device.setDeviceName("n/a");
        device.setModelName("n/a");
        device.setDeviceUpTime("n/a");
        device.setFirmwareVersion("n/a");
        return device;
    }

    private Network getDefaultNetworkValues() {
        Network network = new Network();
        LAN lan = new LAN();
        lan.setLanIp("n/a");
        lan.setLanMac("n/a");
        network.setLAN(lan);
        return network;
    }
}
