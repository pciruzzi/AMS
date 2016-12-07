package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.Partner;
import java.lang.reflect.Type;

public class PartnerAdapter extends ActorAdapter implements JsonSerializer<Partner> {

    public JsonElement serialize(Partner partner, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = super.serialize(partner, type, jsc);
        jsonObject.addProperty("location", partner.getLocation());
        jsonObject.addProperty("address", partner.getAddress());
        jsonObject.addProperty("telephone", partner.getTelephone());
        return jsonObject;
    }

}
