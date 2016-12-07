package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.FSD;
import java.lang.reflect.Type;

public class FSDAdapter extends ActorAdapter implements JsonSerializer<FSD> {

    public JsonElement serialize(FSD fsd, Type type, JsonSerializationContext fsc) {
        return super.serialize(fsd, type, fsc);
    }

}
