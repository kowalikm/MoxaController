package pl.appcoders.moxacontroller.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.appcoders.moxacontroller.database.entity.MappedInput;

@Dao
public interface MappedInputDao {
    @Query("SELECT * FROM mapped_inputs")
    public LiveData<List<MappedInput>> findAll();

    @Query("SELECT * FROM mapped_inputs WHERE id = :id")
    public MappedInput findById(long id);

    @Query("SELECT * FROM mapped_inputs WHERE mapped_name = :mappedName")
    public MappedInput findByName(String mappedName);

    @Query("DELETE FROM mapped_inputs WHERE id = :id")
    public void deleteById(long id);

    @Insert
    public long insert(MappedInput mappedInput);

    @Update
    public void update(MappedInput mappedInput);

    @Delete
    public void delete(MappedInput mappedInput);
}
