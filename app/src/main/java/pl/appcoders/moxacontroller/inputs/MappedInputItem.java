package pl.appcoders.moxacontroller.inputs;

import pl.appcoders.moxacontroller.database.entity.MappedInput;

public class MappedInputItem {
    private long id;
    private String mappedName;
    private int counterValue;
    private int apiIndex;
    private boolean isLoaded;

    public MappedInputItem(long id, String mappedName) {
        this.id = id;
        this.mappedName = mappedName;
        isLoaded = false;
    }

    public MappedInputItem(MappedInput mappedInput) {
        this.id = mappedInput.getId();
        this.mappedName = mappedInput.getMappedName();
        this.apiIndex = mappedInput.getApiIndex();
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

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}
