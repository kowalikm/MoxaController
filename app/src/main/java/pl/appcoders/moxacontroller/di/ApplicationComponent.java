package pl.appcoders.moxacontroller.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.appcoders.moxacontroller.systeminfo.SystemInfoViewModel;

@Singleton
@Component(modules = {AppModule.class, RestModule.class})
public interface ApplicationComponent {
    void inject(SystemInfoViewModel systemInfoViewModel);
}
