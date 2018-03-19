package pl.appcoders.moxacontroller.inputs.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Io {

    @SerializedName("di")
    @Expose
    private List<Di> di = new ArrayList<Di>();

    public List<Di> getDi() {
        return di;
    }

    public void setDi(List<Di> di) {
        this.di = di;
    }

}
