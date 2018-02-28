package pl.appcoders.moxacontroller.systeminfo.service;

import pl.appcoders.moxacontroller.systeminfo.dto.SystemInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface SystemInfoService {

    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @GET("sysinfo")
    Call<SystemInfo> getSystemInfo();
}
