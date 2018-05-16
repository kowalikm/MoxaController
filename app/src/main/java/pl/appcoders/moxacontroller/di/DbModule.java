package pl.appcoders.moxacontroller.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.appcoders.moxacontroller.database.AppDatabase;
import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.database.dao.MappedRelayDao;

/**
 * Created by mkowalik on 19.03.18.
 */

@Module
class DbModule {

    @Provides
    @Singleton
    MappedInputDao providesMappedInputDao(Application application) {
        return AppDatabase.getInstance(application.getApplicationContext()).mappedInputDao();
    }

    @Provides
    @Singleton
    MappedRelayDao providesMappedRelayDao(Application application) {
        return AppDatabase.getInstance(application.getApplicationContext()).mappedRelayDao();
    }

}
