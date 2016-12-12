package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.CV;
import java.lang.reflect.Type;

public class CVAdapter implements JsonSerializer<CV> {

    @Override
    public JsonElement serialize(CV cv, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", cv.getId());
        jsonObject.addProperty("name", cv.getName());
        return jsonObject;
    }

}
