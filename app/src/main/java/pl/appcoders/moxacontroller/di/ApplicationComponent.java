package pl.appcoders.moxacontroller.di;


import javax.inject.Singleton;

import dagger.Component;
import pl.appcoders.moxacontroller.inputs.MapInputActivity;
import pl.appcoders.moxacontroller.inputs.MappedInputDetailsActivity;
import pl.appcoders.moxacontroller.inputs.MappedInputsViewModel;
import pl.appcoders.moxacontroller.relays.MapRelayActivity;
import pl.appcoders.moxacontroller.relays.MappedRelayDetailsActivity;
import pl.appcoders.moxacontroller.relays.MappedRelaysViewModel;
import pl.appcoders.moxacontroller.systeminfo.SystemInfoViewModel;

@Singleton
@Component(modules = {AppModule.class, RestModule.class, DbModule.class})
public interface ApplicationComponent {
    void inject(SystemInfoViewModel systemInfoViewModel);

    void inject(MappedInputsViewModel mappedInputsViewModel);
    void inject(MapInputActivity mapInputActivity);
    void inject(MappedInputDetailsActivity mappedInputDetailsActivity);

    void inject(MappedRelaysViewModel mappedRelaysViewModel);
    void inject(MapRelayActivity mapRelayActivity);
    void inject(MappedRelayDetailsActivity mappedRelayDetailsActivity);

}
