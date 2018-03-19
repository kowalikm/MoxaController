package pl.appcoders.moxacontroller.systeminfo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.Arrays;

import javax.inject.Inject;

import pl.appcoders.moxacontroller.App;
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import pl.appcoders.moxacontroller.systeminfo.dto.Device;
import pl.appcoders.moxacontroller.systeminfo.dto.LAN;
import pl.appcoders.moxacontroller.systeminfo.dto.Network;
import pl.appcoders.moxacontroller.systeminfo.dto.SysInfo;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;
import pl.appcoders.moxacontroller.systeminfo.service.SystemInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SystemInfoViewModel extends ViewModel {
    private MutableLiveData<String> isConnectedMutableLiveData;
    private MutableLiveData<SystemInfo> systemInfoMutableLiveData;
    private OnRestActionListener onRestActionListener;

    @Inject
    SystemInfoService systemInfoService;

    public SystemInfoViewModel() {
        App.getInstance().getApplicationComponent().inject(this);
        refresh();
    }


    public void registerOnRestActionListener(OnRestActionListener onRestActionListener) {
        this.onRestActionListener = onRestActionListener;
        Log.i("Register", "Registered");
    }

    public void unregisterOnRestActionListener() {
        this.onRestActionListener = null;
        Log.i("Register", "unRegistered");
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
        if(isOnRestActionListenerRegistered()) {
            onRestActionListener.requestStartedAction();
            Log.i("requested", "action");
        }

        systemInfoService.getSystemInfo().enqueue(new Callback<SystemInfo>() {
            @Override
            public void onResponse(Call<SystemInfo> call, Response<SystemInfo> response) {
                if(response.isSuccessful()) {
                    systemInfoMutableLiveData.postValue(response.body());
                    isConnectedMutableLiveData.postValue("Online");
                } else {
                    setDefaultValues();
                    Log.w("GetSystemInfoResponse", response.message());
                }

                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.responseAction(response);
                }
            }

            @Override
            public void onFailure(Call<SystemInfo> call, Throwable t) {
                setDefaultValues();
                Log.w("GetSystemInfoFailure", t.getMessage());
                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.failureAction(t);
                }
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

    private boolean isOnRestActionListenerRegistered() {
        return this.onRestActionListener != null;
    }
}
