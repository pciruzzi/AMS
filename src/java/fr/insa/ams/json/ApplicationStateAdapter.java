package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.stateMachine.ApplicationState;
import java.lang.reflect.Type;

public class ApplicationStateAdapter extends ActorAdapter implements JsonSerializer<ApplicationState> {

    public JsonElement serialize(ApplicationState state, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", state.name());
        jsonObject.addProperty("message", state.getMessage());
        return jsonObject;
    }

}
