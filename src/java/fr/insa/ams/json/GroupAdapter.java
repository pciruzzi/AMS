package fr.insa.ams.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import fr.insa.ams.Group;
import java.lang.reflect.Type;

public class GroupAdapter implements JsonDeserializer<Group> {

    public Group deserialize(JsonElement element, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return new Group(element.getAsString());
    }

}
