
package pl.appcoders.moxacontroller.relays.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Io {

    @SerializedName("relay")
    @Expose
    private List<Relay> relay = null;

    public List<Relay> getRelay() {
        return relay;
    }

    public void setRelay(List<Relay> relay) {
        this.relay = relay;
    }

}
