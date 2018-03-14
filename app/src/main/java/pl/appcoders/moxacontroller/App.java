package pl.appcoders.moxacontroller;

import android.app.Application;

import pl.appcoders.moxacontroller.di.AppModule;
import pl.appcoders.moxacontroller.di.ApplicationComponent;
import pl.appcoders.moxacontroller.di.DaggerApplicationComponent;

/**
 * Created by mkowalik on 08.03.18.
 */

public class App extends Application {

    private static App app;

    private ApplicationComponent applicationComponent;

    public  static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        applicationComponent = DaggerApplicationComponent.builder().appModule(new AppModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
