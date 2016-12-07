package fr.insa.ams.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.insa.ams.Student;
import java.lang.reflect.Type;

public class StudentAdapter extends ActorAdapter implements JsonSerializer<Student> {

    @Override
    public JsonElement serialize(Student student, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = super.serialize(student, type, jsc);
        jsonObject.addProperty("year", student.getYear());
        jsonObject.addProperty("pathway", student.getPathway());
        jsonObject.addProperty("address", student.getAddress());
        jsonObject.addProperty("telephone", student.getTelephone());
        return jsonObject;
    }

}
