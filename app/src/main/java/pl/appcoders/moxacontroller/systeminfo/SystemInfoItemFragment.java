package pl.appcoders.moxacontroller.systeminfo;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.main.OnRefreshActionListener;
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import pl.appcoders.moxacontroller.systeminfo.dto.Device;
import pl.appcoders.moxacontroller.systeminfo.dto.LAN;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;

public class SystemInfoItemFragment extends Fragment implements OnRefreshActionListener {

    private SystemInfoViewModel systemInfoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.device_status);
        systemInfoViewModel = ViewModelProviders.of(this)
                .get(SystemInfoViewModel.class);

        registerRestActionListener();

        systemInfoViewModel.getIsConnected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String isConnected) {
                setTextViewText(R.id.isConnectedValue, isConnected);
            }
        });
        systemInfoViewModel.getSystemInfo().observe(this, new Observer<SystemInfo>() {
            @Override
            public void onChanged(@Nullable SystemInfo systemInfo) {
                Log.i("Systeminfo", "changed!");
                updateDeviceInfo(systemInfo);
                updateNetworkInfo(systemInfo);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statusitem, container, false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        systemInfoViewModel.unregisterOnRestActionListener();
        super.onStop();
    }

    @Override
    public void refreshAction() {
        systemInfoViewModel.refresh();
    }

    private void registerRestActionListener() {
        Activity activity = getActivity();
        if(activity instanceof OnRestActionListener) {
            systemInfoViewModel.registerOnRestActionListener((OnRestActionListener)getActivity());
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnRestActionListener!");
        }
    }

    private void setTextViewText(int id, String text) {
        TextView textView = getActivity().findViewById(id);
        textView.setText(text);
    }

    private void updateNetworkInfo(@Nullable SystemInfo systemInfo) {
        LAN lan = systemInfo.getSysInfo().getNetwork().getLAN();
        setTextViewText(R.id.lanMacValue, lan.getLanMac());
        setTextViewText(R.id.lanIpValue, lan.getLanIp());
    }

    private void updateDeviceInfo(@Nullable SystemInfo systemInfo) {
        Device device = systemInfo.getSysInfo().getDevice().get(0);
        setTextViewText(R.id.modelNameValue, device.getModelName());
        setTextViewText(R.id.deviceNameValue, device.getDeviceName());
        setTextViewText(R.id.deviceUpTimeValue, device.getDeviceUpTime());
        setTextViewText(R.id.firmwareVersionValue, device.getFirmwareVersion());
    }
}
