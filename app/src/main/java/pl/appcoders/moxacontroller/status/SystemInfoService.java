package pl.appcoders.moxacontroller.status;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mkowalik on 26.02.18.
 */

public interface SystemInfoService {
    @GET("sysinfo")
    Call<SystemInfo> getSystemInfo();
}
