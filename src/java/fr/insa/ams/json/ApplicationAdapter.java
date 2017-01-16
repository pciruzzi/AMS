package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.Application;
import fr.insa.ams.CV;
import java.lang.reflect.Type;

public class ApplicationAdapter implements JsonSerializer<Application> {

    @Override
    public JsonElement serialize(Application application, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", application.getId());
        jsonObject.addProperty("idStudent", application.getStudent().getId());
        jsonObject.addProperty("idPartner", application.getPartner().getId());
        jsonObject.addProperty("namePartner", application.getPartner().getName());
        jsonObject.addProperty("idCoordinator", application.getCoordinator().getId());
        jsonObject.addProperty("idOffer", application.getOfferID());
        jsonObject.addProperty("state", application.getState().toString());
        jsonObject.addProperty("coverLetter", application.getCoverLetter());
        CV cv = application.getCv();
        int cvId = -1;
        if (cv != null)  cvId = cv.getId();
        jsonObject.addProperty("idCv", cvId);
        return jsonObject;
    }

}
