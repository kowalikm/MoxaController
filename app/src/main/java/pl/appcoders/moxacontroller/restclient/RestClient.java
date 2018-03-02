package pl.appcoders.moxacontroller.restclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mkowalik on 01.03.18.
 */

public class RestClient {
    private static Retrofit instance;

    public static Retrofit getRetrofitInstance(Context context) {
        if(instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(getApiAddress(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return instance;
    }

    private RestClient() {

    }

    @NonNull
    private static String getApiAddress(Context context) {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String apiAddress = defaultSharedPreferences.getString("deviceAddress", "");
        String apiEndpoint = defaultSharedPreferences.getString("restfulApiEndpoint", "/api/slot/0/");
        return apiAddress + apiEndpoint;
    }
}
