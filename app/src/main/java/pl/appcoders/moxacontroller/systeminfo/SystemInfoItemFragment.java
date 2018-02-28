package pl.appcoders.moxacontroller.systeminfo;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.main.OnRefreshActionListener;
import pl.appcoders.moxacontroller.systeminfo.service.SystemInfoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SystemInfoItemFragment extends Fragment implements OnRefreshActionListener {
    private RecyclerView recyclerView;

    private SystemInfoService systemInfoService;

    private SystemInfoCallback systemInfoCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.device_status);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statusitem_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new SystemInfoItemRecyclerViewAdapter(new SystemInfoContainer(context)));

            systemInfoCallback = new SystemInfoCallback(recyclerView, getActivity());

            createSystemInfoService();

            startRequest();
        }
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
        Toast.makeText(getActivity(), R.string.refresh_toast, Toast.LENGTH_LONG).show();
        startRequest();
    }

    private void startRequest() {
        systemInfoService.getSystemInfo().enqueue(systemInfoCallback);
    }

    private void createSystemInfoService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApiAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        systemInfoService = retrofit.create(SystemInfoService.class);
    }

    @NonNull
    private String getApiAddress() {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String apiAddress = defaultSharedPreferences.getString("deviceAddress", "");
        String apiEndpoint = defaultSharedPreferences.getString("restfulApiEndpoint", "/api/slot/0/");
        return apiAddress + apiEndpoint;
    }
}
