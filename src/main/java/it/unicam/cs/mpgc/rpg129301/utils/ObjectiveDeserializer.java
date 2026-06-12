package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg129301.model.objectives.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ObjectiveDeserializer implements JsonDeserializer<Objective> {

    // Internal registry mapping node types to their corresponding Java classes
    private static final Map<String, Class<? extends Objective>> registry = new HashMap<>();

    // Static block to initialize the registry when the class is loaded into memory
    static {
        registry.put("change_user", ChangeUserObjective.class);

        // Future objectives can be easily registered here:
        registry.put("delete_file", DeleteFileObjective.class);
    }

    /**
     * Deserializes a JSON element into the appropriate Objective subclass based on its "type" field.
     * @param json The JSON data being deserialized.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context The deserialization context to delegate the actual object creation.
     * @return A fully instantiated Objective object of the correct subclass.
     * @throws JsonParseException if the type attribute is missing or refers to an unregistered class.
     */
    @Override
    public Objective deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Convert into a JSON object to easily access its properties
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the required 'type' field to determine which Objective subclass to instantiate
        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Missing 'type' attribute in JSON file for this objective");
        }

        String typeName = typeElement.getAsString();

        // Dynamically resolve the corresponding Java class from the internal registry (e.g., "change_user" -> ChangeUserObjective.class)
        Class<? extends Objective> objectiveClass = registry.get(typeName);

        // Report an error  if the JSON contains an objective type that hasn't been registered in the system
        if (objectiveClass == null) {
            throw new JsonParseException("Unregistered or unknown objective type: " + typeName);
        }

        // Delegate the actual object creation and field population back to GSON using the resolved class
        return context.deserialize(jsonObject, objectiveClass);
    }
}