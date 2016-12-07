package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.Application;
import java.lang.reflect.Type;

public class ApplicationAdapter implements JsonSerializer<Application> {

    @Override
    public JsonElement serialize(Application application, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", application.getId());
        jsonObject.addProperty("idStudent", application.getStudent().getId());
        jsonObject.addProperty("idPartner", application.getPartner().getId());
        jsonObject.addProperty("idCoordinator", application.getCoordinator().getId());
        jsonObject.addProperty("idOffer", application.getOfferID());
        return jsonObject;
    }

}
