package pl.appcoders.moxacontroller.inputs;

import pl.appcoders.moxacontroller.database.entity.MappedInput;

public class MappedInputItem {
    private long id;
    private String mappedName;
    private int counterValue;
    private int apiIndex;
    private DigitalInputMode mode;
    private DigitalInputStatus status;

    public enum DigitalInputMode {
        INPUT_MODE,
        COUNTER_MODE
    }

    public enum DigitalInputStatus {
        OFF_STATUS,
        ON_STATUS
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
}
