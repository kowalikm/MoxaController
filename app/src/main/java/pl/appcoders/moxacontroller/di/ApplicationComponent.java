package pl.appcoders.moxacontroller.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.appcoders.moxacontroller.inputs.MapInputActivity;
import pl.appcoders.moxacontroller.inputs.MappedInputDetailsActivity;
import pl.appcoders.moxacontroller.inputs.MappedInputViewModel;
import pl.appcoders.moxacontroller.systeminfo.SystemInfoViewModel;

@Singleton
@Component(modules = {AppModule.class, RestModule.class, DbModule.class})
public interface ApplicationComponent {
    void inject(SystemInfoViewModel systemInfoViewModel);
    void inject(MappedInputViewModel mappedInputViewModel);
    void inject(MapInputActivity mapInputActivity);
    void inject(MappedInputDetailsActivity mappedInputDetailsActivity);
}
