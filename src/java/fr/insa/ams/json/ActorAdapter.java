package fr.insa.ams.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import fr.insa.ams.Actor;
import java.lang.reflect.Type;

public class ActorAdapter {

    public JsonObject serialize(Actor actor, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", actor.getId());
        jsonObject.addProperty("name", actor.getName());
        jsonObject.addProperty("password", actor.getPassword());
        jsonObject.addProperty("email", actor.getEmail());
        jsonObject.addProperty("group", actor.getGroup().getName());
        return jsonObject;
    }

}
