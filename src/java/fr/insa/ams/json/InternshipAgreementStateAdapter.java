package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.stateMachine.InternshipAgreementState;
import java.lang.reflect.Type;

public class InternshipAgreementStateAdapter implements JsonSerializer<InternshipAgreementState> {

    @Override
    public JsonElement serialize(InternshipAgreementState state, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", state.name());
        jsonObject.addProperty("message", state.getMessage());
        return jsonObject;
    }

}