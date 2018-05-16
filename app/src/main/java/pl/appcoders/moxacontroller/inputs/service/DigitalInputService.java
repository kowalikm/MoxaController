package pl.appcoders.moxacontroller.inputs.service;

import com.google.gson.JsonObject;

import pl.appcoders.moxacontroller.inputs.dto.DigitalInputs;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DigitalInputService {

    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @GET("io/di")
    Call<DigitalInputs> getDigitalInputs();


    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @PUT("io/di/{id}/diCounterReset")
    Call<Void> resetDiCounter(@Path("id") String id, @Body JsonObject counterResetJsonObject);
}
