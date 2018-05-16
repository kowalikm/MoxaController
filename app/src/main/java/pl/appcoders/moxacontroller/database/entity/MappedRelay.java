package pl.appcoders.moxacontroller.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mkowalik on 16.05.18.
 */
@Entity(tableName = "mapped_relays", indices = {
        @Index(value = {"mapped_name"}, unique = true)
})
public class MappedRelay {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "mapped_name")
    private String mappedName;

    @ColumnInfo(name = "api_index")
    private int apiIndex;

    public MappedRelay(String mappedName, int apiIndex) {
        this.mappedName = mappedName;
        this.apiIndex = apiIndex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    public int getApiIndex() {
        return apiIndex;
    }

    public void setApiIndex(int apiIndex) {
        this.apiIndex = apiIndex;
    }
}
