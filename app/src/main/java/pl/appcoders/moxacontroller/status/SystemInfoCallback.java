package pl.appcoders.moxacontroller.status;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import pl.appcoders.moxacontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mkowalik on 27.02.18.
 */

public class SystemInfoCallback implements Callback<SystemInfo> {

    private final Activity activity;
    private final RecyclerView recyclerView;

    public SystemInfoCallback(RecyclerView recyclerView, Activity activity) {
        this.recyclerView = recyclerView;
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<SystemInfo> call, Response<SystemInfo> response) {
        if(response.isSuccessful()) {
            StatusContainer statusContainer = new StatusContainer(response.body(), activity);
            recyclerView.setAdapter(new StatusItemRecyclerViewAdapter(statusContainer));
            Toast.makeText(activity, activity.getString(R.string.device_status_refresh), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<SystemInfo> call, Throwable t) {
        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }
}
