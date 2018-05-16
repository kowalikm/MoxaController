package pl.appcoders.moxacontroller.relays.service;

import com.google.gson.JsonObject;

import pl.appcoders.moxacontroller.relays.dto.Relays;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mkowalik on 17.05.18.
 */

public interface RelaysService {
    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @GET("io/relay")
    Call<Relays> getRelays();

    @Headers({"Accept: vdn.dac.v1", "Content-Type: application/json"})
    @PUT("io/relay/{id}/relayStatus")
    Call<Void> changeRelayState(@Path("id") String s, @Body JsonObject changeRelayStateJsonObject);
}
