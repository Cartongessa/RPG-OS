package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

import java.lang.reflect.Type;

public class FileSystemNodeDeserializer implements JsonDeserializer<FileSystemNode> {
    /**
     * Utility to deserialize data from json
     * @param json The JSON data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return A FileSystemNode instance based on the "type" field in the JSON
     * @throws JsonParseException
     */
    @Override
    public FileSystemNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JsonParseException("Missing 'type' attribute in JSON file for this node");
        }

        String type = typeElement.getAsString();

        if ("directory".equals(type)) {
            return context.deserialize(jsonObject, GameDirectory.class);
        } else if ("file".equals(type)) {
            return context.deserialize(jsonObject, GameFile.class);
        } else {
            throw new JsonParseException("Unknown node type: " + type);
        }
    }
}