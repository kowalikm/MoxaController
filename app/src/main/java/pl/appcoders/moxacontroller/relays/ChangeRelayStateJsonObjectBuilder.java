package pl.appcoders.moxacontroller.relays;

import com.google.gson.JsonObject;

/**
 * Created by mkowalik on 16.05.18.
 */

public class ChangeRelayStateJsonObjectBuilder {
    public static JsonObject buildChangeRelayStateJsonObject(Integer relayIndex, Integer newState) {
        JsonObject changeRelayStateJsonObject = new JsonObject();
        changeRelayStateJsonObject.addProperty("relayStatus", newState);

        JsonObject relayIndexJsonObject = new JsonObject();
        relayIndexJsonObject.add(relayIndex.toString(), changeRelayStateJsonObject);

        JsonObject relayJsonObject = new JsonObject();
        relayJsonObject.add("relay", relayIndexJsonObject);

        JsonObject mainJsonObject = new JsonObject();

        mainJsonObject.addProperty("slot", 0);
        mainJsonObject.add("io", relayJsonObject);

        return mainJsonObject;
    }
}
