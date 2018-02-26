package pl.appcoders.moxacontroller.status;

/**
 * Created by mkowalik on 26.02.18.
 */

class StatusItem {
    private String id;

    private String data;

    public StatusItem(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
