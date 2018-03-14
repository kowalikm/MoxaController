package pl.appcoders.moxacontroller.systeminfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.main.OnRefreshActionListener;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;

public class SystemInfoItemFragment extends Fragment implements OnRefreshActionListener {

    private SystemInfoViewModel systemInfoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.device_status);
        systemInfoViewModel = ViewModelProviders.of(this)
                .get(SystemInfoViewModel.class);
        systemInfoViewModel.getIsConnected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String isConnected) {
                TextView textView = getActivity().findViewById(R.id.isConnectedText);
                textView.setText(isConnected);
            }
        });
        systemInfoViewModel.getSystemInfo().observe(this, new Observer<SystemInfo>() {
            @Override
            public void onChanged(@Nullable SystemInfo systemInfo) {
                TextView textView = getActivity().findViewById(R.id.modelNameText);
                textView.setText(systemInfo.getSysInfo().getDevice().get(0).getModelName());
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
    public void refreshAction() {
        systemInfoViewModel.refresh();
    }
}
