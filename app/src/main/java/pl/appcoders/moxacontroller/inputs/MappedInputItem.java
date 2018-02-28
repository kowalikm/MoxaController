package pl.appcoders.moxacontroller.inputs;

/**
 * Created by mkowalik on 28.02.18.
 */

public class MappedInputItem {
    private final long id;
    private final String mappedName;
    private final int counterValue;

    public MappedInputItem(long id, String mappedName, int counterValue) {
        this.id = id;
        this.mappedName = mappedName;
        this.counterValue = counterValue;
    }

    public long getId() {
        return id;
    }

    public String getMappedName() {
        return mappedName;
    }

    public int getCounterValue() {
        return counterValue;
    }
}
