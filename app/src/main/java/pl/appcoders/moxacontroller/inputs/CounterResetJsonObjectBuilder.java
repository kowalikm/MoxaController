package pl.appcoders.moxacontroller.inputs;

import com.google.gson.JsonObject;

/**
 * Created by mkowalik on 16.05.18.
 */

public class CounterResetJsonObjectBuilder {
    public static JsonObject buildCounterResetJsonObject(Integer diIndex) {
        JsonObject diCounterResetJsonObject = new JsonObject();
        diCounterResetJsonObject.addProperty("diCounterReset", 1);

        JsonObject diIndexJsonObject = new JsonObject();
        diIndexJsonObject.add(diIndex.toString(), diCounterResetJsonObject);

        JsonObject diJsonObject = new JsonObject();
        diJsonObject.add("di", diIndexJsonObject);

        JsonObject mainJsonObject = new JsonObject();

        mainJsonObject.addProperty("slot", 0);
        mainJsonObject.add("io", diJsonObject);

        return mainJsonObject;
    }
}
