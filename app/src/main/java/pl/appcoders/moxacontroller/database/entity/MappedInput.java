package pl.appcoders.moxacontroller.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "mapped_inputs", indices = {
        @Index(value = {"mapped_name"}, unique = true)
})
public class MappedInput {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "mapped_name")
    public String mappedName;

    @ColumnInfo(name = "api_index")
    public int apiIndex;
}
