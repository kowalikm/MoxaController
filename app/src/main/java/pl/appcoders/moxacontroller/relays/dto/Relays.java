
package pl.appcoders.moxacontroller.relays.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relays {

    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("io")
    @Expose
    private Io io;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Io getIo() {
        return io;
    }

    public void setIo(Io io) {
        this.io = io;
    }

}
