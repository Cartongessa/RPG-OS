package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FileSystemNodeDeserializer implements JsonDeserializer<FileSystemNode> {

    // Internal registry mapping node types (e.g., "directory", "file") to their corresponding Java classes
    private static final Map<String, Class<? extends FileSystemNode>> registry = new HashMap<>();

    // Static block to initialize the registry when the class is loaded into memory
    static {
        registry.put("directory", GameDirectory.class);
        registry.put("file", GameFile.class);
    }

    /**
     * Deserializes a JSON element into the appropriate FileSystemNode subclass based on its "type" field.
     * @param json The JSON data being deserialized.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context The deserialization context to delegate the actual object creation.
     * @return A fully instantiated FileSystemNode object of the correct subclass.
     * @throws JsonParseException if the type attribute is missing or refers to an unregistered class.
     */
    @Override
    public FileSystemNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Convert into a JSON object to easily access its properties
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the required 'type' field to determine which FileSystemNode subclass to instantiate
        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Missing 'type' attribute in JSON file for this node");
        }

        String typeName = typeElement.getAsString();

        // Dynamically resolve the corresponding Java class from the internal registry (e.g. "directory" -> GameDirectory.class)
        Class<? extends FileSystemNode> nodeClass = registry.get(typeName);

        // Report an error if the JSON contains a node that hasn't been registered in the system
        if (nodeClass == null) {
            throw new JsonParseException("Unregistered or unknown node type: " + typeName);
        }

        // Delegate the actual object creation and field population back to GSON using the resolved class
        return context.deserialize(jsonObject, nodeClass);
    }
}