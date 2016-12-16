package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.Administrator;
import java.lang.reflect.Type;

public class AdministratorAdapter extends ActorAdapter implements JsonSerializer<Administrator> {

    @Override
    public JsonElement serialize(Administrator admin, Type type, JsonSerializationContext fsc) {
        return super.serialize(admin, type, fsc);
    }

}
