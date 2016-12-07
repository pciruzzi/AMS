package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.ClassCoordinator;
import java.lang.reflect.Type;

public class ClassCoordinatorAdapter extends ActorAdapter implements JsonSerializer<ClassCoordinator> {

    @Override
    public JsonElement serialize(ClassCoordinator coordinator, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = super.serialize(coordinator, type, jsc);
        jsonObject.addProperty("year", coordinator.getYear());
        jsonObject.addProperty("pathway", coordinator.getPathway());
        return jsonObject;
    }

}
