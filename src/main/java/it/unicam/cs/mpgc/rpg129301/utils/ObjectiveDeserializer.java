package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg129301.model.objectives.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ObjectiveDeserializer implements JsonDeserializer<Objective> {

    private final Map<String, Class<? extends Objective>> registry = new HashMap<>();

    public ObjectiveDeserializer() {
        registry.put("change_user", ChangeUserObjective.class);
    }

    @Override
    public Objective deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Type not found in JSON for objective");
        }

        String typeName = typeElement.getAsString();

        Class<? extends Objective> objectiveClass = registry.get(typeName);

        if (objectiveClass == null) {
            throw new JsonParseException("Unregistered objective: " + typeName);
        }

        return context.deserialize(jsonObject, objectiveClass);
    }
}