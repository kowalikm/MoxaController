package pl.appcoders.moxacontroller.inputs;

/**
 * Created by mkowalik on 28.02.18.
 */

public class MappedInputItem {
    private String mappedName;
    private int counterValue;

    public MappedInputItem(String mappedName, int counterValue) {
        this.mappedName = mappedName;
        this.counterValue = counterValue;
    }

    public String getMappedName() {
        return mappedName;
    }

    public int getCounterValue() {
        return counterValue;
    }
}
