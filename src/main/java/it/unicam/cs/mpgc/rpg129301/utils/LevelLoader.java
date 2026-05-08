package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Objects;

public class LevelLoader {

    private final Gson gson;

    public LevelLoader() {
        // The deserializer looks at "type"
        JsonDeserializer<FileSystemNode> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();

            if ("directory".equals(type)) {
                return context.deserialize(json, GameDirectory.class);
            } else if ("file".equals(type)) {
                return context.deserialize(json, GameFile.class);
            }
            throw new JsonParseException("Unknown type of node: " + type);
        };

        this.gson = new GsonBuilder()
                .registerTypeAdapter(FileSystemNode.class, deserializer)
                .create();
    }

    /**
     * Loads a level based on its path
     * @param resourcePath The relative path to the level
     * @return The root directory of the loaded level
     */
    private GameDirectory loadLevel(String resourcePath) {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourcePath)))) {

            GameDirectory root = gson.fromJson(reader, GameDirectory.class);
            restoreParents(root);
            return root;
        } catch (Exception e) {
            throw new RuntimeException("Error while loading the level: " + resourcePath, e);
        }
    }

    /**
     * Loads a level based on its index
     * Maps index '1' to 'level1.json'
     * @param levelIndex The integer index of the level
     * @return The root directory of the loaded level
     */
    public GameDirectory loadLevel(int levelIndex) {
        String fileName = "level" + levelIndex + ".json";

        // Verify if the resource exists before attempting to read
        if (getClass().getClassLoader().getResource(fileName) == null) {
            throw new IllegalArgumentException("Level file not found: " + fileName);
        }

        return loadLevel(fileName); // Reuse the existing String-based loading logic
    }

    /**
     * Recursively restores parent references in the directory tree
     * @param currentDir The current directory to process
     */
    private void restoreParents(GameDirectory currentDir) {
        if (currentDir.getChildren() == null) return;
        for (FileSystemNode child : currentDir.getChildren().values()) {
            if (child instanceof GameDirectory childDir) {
                childDir.setParent(currentDir);
                restoreParents(childDir);
            }
        }
    }
}