package pl.appcoders.moxacontroller.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.database.entity.MappedInput;

@Database(entities = {MappedInput.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract MappedInputDao mappedInputDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            //Use async methods for query handling if ui gets locked
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "moxa-controller-db").allowMainThreadQueries().build();
        }

        return instance;
    }
}
