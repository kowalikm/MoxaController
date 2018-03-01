package pl.appcoders.moxacontroller.inputs.service;

import pl.appcoders.moxacontroller.inputs.dto.Di;
import pl.appcoders.moxacontroller.inputs.dto.DigitalInputList;
import pl.appcoders.moxacontroller.inputs.dto.ErrorDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mkowalik on 28.02.18.
 */

public interface DigitalInputService {

    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @GET("io/di")
    Call<DigitalInputList> getDiList();


    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @PUT("io/di/{id}")
    Call<ErrorDto> updateDi(@Path("id") String id, @Body Di di);
}
