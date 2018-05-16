package pl.appcoders.moxacontroller.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.appcoders.moxacontroller.database.entity.MappedRelay;

/**
 * Created by mkowalik on 16.05.18.
 */

@Dao
public interface MappedRelayDao {
    @Query("SELECT * FROM mapped_relays")
    public LiveData<List<MappedRelay>> findAll();

    @Query("SELECT * FROM mapped_relays WHERE id = :id")
    public MappedRelay findById(long id);

    @Query("SELECT * FROM mapped_relays WHERE mapped_name = :mappedName")
    public MappedRelay findByName(String mappedName);

    @Query("DELETE FROM mapped_relays WHERE id = :id")
    public void deleteById(long id);

    @Insert
    public long insert(MappedRelay mappedRelay);

    @Update
    public void update(MappedRelay mappedRelay);

    @Delete
    public void delete(MappedRelay mappedRelay);
}
