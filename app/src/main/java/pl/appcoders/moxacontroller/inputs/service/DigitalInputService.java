package pl.appcoders.moxacontroller.inputs.service;

import pl.appcoders.moxacontroller.inputs.dto.DigitalInputs;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DigitalInputService {

    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @GET("io/di")
    Call<DigitalInputs> getDigitalInputs();


//    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
//    @PUT("io/di/{id}")
//    Call<ErrorDto> updateDi(@Path("id") String id, @Body Di di);
}
