package pl.appcoders.moxacontroller.relays;

import android.os.Parcel;
import android.os.Parcelable;

import pl.appcoders.moxacontroller.database.entity.MappedRelay;

/**
 * Created by mkowalik on 16.05.18.
 */

public class MappedRelayItem implements Parcelable {
    private long id;
    private String mappedName;
    private int apiIndex;
    private RelayMode mode;
    private RelayStatus status;

    public enum RelayMode {
        RELAY(0),
        PULSE(1);

        private final int value;
        private RelayMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum RelayStatus {
        OFF(0),
        ON(1);

        private final int value;
        private RelayStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public MappedRelayItem(long id, String mappedName) {
        this.id = id;
        this.mappedName = mappedName;
    }

    public MappedRelayItem(MappedRelay mappedRelay) {
        id = mappedRelay.getId();
        mappedName = mappedRelay.getMappedName();
        apiIndex = mappedRelay.getApiIndex();
    }

    protected MappedRelayItem(Parcel in) {
        id = in.readLong();
        mappedName = in.readString();
        apiIndex = in.readInt();
        mode = RelayMode.values()[in.readInt()];
        status = RelayStatus.values()[in.readInt()];
    }

    public static final Creator<MappedRelayItem> CREATOR = new Creator<MappedRelayItem>() {
        @Override
        public MappedRelayItem createFromParcel(Parcel in) {
            return new MappedRelayItem(in);
        }

        @Override
        public MappedRelayItem[] newArray(int size) {
            return new MappedRelayItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(mappedName);
        parcel.writeInt(apiIndex);
        parcel.writeInt(mode.getValue());
        parcel.writeInt(status.getValue());
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

    public RelayMode getMode() {
        return mode;
    }

    public void setMode(RelayMode mode) {
        this.mode = mode;
    }

    public RelayStatus getStatus() {
        return status;
    }

    public void setStatus(RelayStatus status) {
        this.status = status;
    }

    public String getInfo() {
        switch (mode) {
            case RELAY:
                return "Status: " + status.name();
            case PULSE:
                return "Pulse: ?";  //TODO: when implement pulse...
            default:
                throw new IllegalArgumentException("MappedInputItem mode not supported!");
        }
    }
}
