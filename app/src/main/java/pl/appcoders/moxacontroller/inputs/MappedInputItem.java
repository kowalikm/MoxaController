package pl.appcoders.moxacontroller.inputs;

import android.os.Parcel;
import android.os.Parcelable;

import pl.appcoders.moxacontroller.database.entity.MappedInput;

public class MappedInputItem implements Parcelable {
    private long id;
    private String mappedName;
    private int counterValue;
    private int apiIndex;
    private DigitalInputMode mode;
    private DigitalInputStatus status;

    public enum DigitalInputMode {
        INPUT(0),
        COUNTER(1);

        private final int value;
        private DigitalInputMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum DigitalInputStatus {
        OFF(0),
        ON(1);

        private final int value;
        private DigitalInputStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public MappedInputItem(long id, String mappedName) {
        this.id = id;
        this.mappedName = mappedName;
    }

    public MappedInputItem(MappedInput mappedInput) {
        id = mappedInput.getId();
        mappedName = mappedInput.getMappedName();
        apiIndex = mappedInput.getApiIndex();
    }

    protected MappedInputItem(Parcel in) {
        id = in.readLong();
        mappedName = in.readString();
        counterValue = in.readInt();
        apiIndex = in.readInt();
        mode = DigitalInputMode.values()[in.readInt()];
        status = DigitalInputStatus.values()[in.readInt()];
    }

    public static final Creator<MappedInputItem> CREATOR = new Creator<MappedInputItem>() {
        @Override
        public MappedInputItem createFromParcel(Parcel in) {
            return new MappedInputItem(in);
        }

        @Override
        public MappedInputItem[] newArray(int size) {
            return new MappedInputItem[size];
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
        parcel.writeInt(counterValue);
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

    public int getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(int counterValue) {
        this.counterValue = counterValue;
    }

    public int getApiIndex() {
        return apiIndex;
    }

    public void setApiIndex(int apiIndex) {
        this.apiIndex = apiIndex;
    }

    public DigitalInputMode getMode() {
        return mode;
    }

    public void setMode(DigitalInputMode mode) {
        this.mode = mode;
    }

    public DigitalInputStatus getStatus() {
        return status;
    }

    public void setStatus(DigitalInputStatus status) {
        this.status = status;
    }

    public String getInfo() {
        switch (mode) {
            case INPUT:
                return "Status: " + status.name();
            case COUNTER:
                return "Counter value: " + counterValue;
            default:
                throw new IllegalArgumentException("MappedInputItem mode not supported!");
        }
    }
}
