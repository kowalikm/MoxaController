package pl.appcoders.moxacontroller.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.appcoders.moxacontroller.inputs.service.DigitalInputService;
import pl.appcoders.moxacontroller.systeminfo.service.SystemInfoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class RestModule {
    private Retrofit retrofit;

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.contentEquals("deviceAddress") || key.contentEquals("restfulApiEndpoint")) {
                    createRetrofitInstance(sharedPreferences);
                }
            }
        });

        return sharedPreferences;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(SharedPreferences sharedPreferences) {
        if(retrofit == null) {
            createRetrofitInstance(sharedPreferences);
        }
        return retrofit;
    }

    @Provides
    SystemInfoService systemInfoService(Retrofit retrofit) {
        return retrofit.create(SystemInfoService.class);
    }

    @Provides
    DigitalInputService digitalInputService(Retrofit retrofit) {
        return retrofit.create(DigitalInputService.class);
    }

    private void createRetrofitInstance(SharedPreferences sharedPreferences) {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getApiBaseUrl(sharedPreferences)).build();
    }

    private String getApiBaseUrl(SharedPreferences sharedPreferences) {
        String apiAddress = sharedPreferences.getString("deviceAddress", "http://127.0.0.1");
        String apiEndpoint = sharedPreferences.getString("restfulApiEndpoint", "/api/slot/0/");
        return apiAddress + apiEndpoint;
    }


}
