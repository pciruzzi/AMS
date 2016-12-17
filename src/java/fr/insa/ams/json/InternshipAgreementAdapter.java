package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.InternshipAgreement;
import java.lang.reflect.Type;

public class InternshipAgreementAdapter implements JsonSerializer<InternshipAgreement> {

    @Override
    public JsonElement serialize(InternshipAgreement agreement, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", agreement.getId());
        jsonObject.addProperty("idStudent", agreement.getStudent().getId());
        jsonObject.addProperty("idPartner", agreement.getPartner().getId());
        jsonObject.addProperty("idCoordinator", agreement.getCoordinator().getId());
        jsonObject.addProperty("idOffer", agreement.getOfferID());
        jsonObject.addProperty("state", agreement.getState().toString());
        return jsonObject;
    }

}
