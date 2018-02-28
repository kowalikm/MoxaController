package pl.appcoders.moxacontroller.systeminfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SystemInfoCallback implements Callback<SystemInfo> {

    private final Activity activity;
    private final RecyclerView recyclerView;

    SystemInfoCallback(RecyclerView recyclerView, Activity activity) {
        this.recyclerView = recyclerView;
        this.activity = activity;
    }

    @Override
    public void onResponse(@NonNull Call<SystemInfo> call, @NonNull Response<SystemInfo> response) {
        if(response.isSuccessful()) {
            SystemInfoContainer systemInfoContainer = new SystemInfoContainer(response.body(), activity);
            recyclerView.setAdapter(new SystemInfoItemRecyclerViewAdapter(systemInfoContainer));
            Toast.makeText(activity, activity.getString(R.string.device_status_refresh), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<SystemInfo> call, @NonNull Throwable t) {
        Toast.makeText(activity, activity.getString(R.string.error) + ": " + t.getMessage(), Toast.LENGTH_LONG).show();

        t.printStackTrace();
    }
}
