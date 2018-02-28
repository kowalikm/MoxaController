package pl.appcoders.moxacontroller.systeminfo;

class SystemInfoItem {
    private String id;

    private String data;

    SystemInfoItem(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
