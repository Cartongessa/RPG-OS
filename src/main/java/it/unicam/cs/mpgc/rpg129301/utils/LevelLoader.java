package it.unicam.cs.mpgc.rpg129301.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LevelLoader {

    /**
     * Loads the required level by index from the resources folder and returns its data as a LevelData object.
     * @param levelIndex The index of the level to load (e.g., 1 for "level1.json")
     * @return The LevelData object containing the level's file system and starting account
     */
    public LevelData loadLevel(int levelIndex) {
        String path = "/levels/level" + levelIndex + ".json";

        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new IllegalArgumentException("File not found: " + path);

            Reader reader = new InputStreamReader(is);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(FileSystemNode.class, new FileSystemNodeDeserializer())
                    .create();

            LevelData levelData = gson.fromJson(reader, LevelData.class);

            if (levelData != null && levelData.getFileSystem() != null) {
                // Root has not got any parent
                linkParents(levelData.getFileSystem(), null);
            }

            return levelData;

        } catch (Exception e) {
            System.err.println("Error while loading level " + levelIndex + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Recursive function to set the parent reference for each node in the file system tree.
     * @param node The current node
     * @param parent The parent of the current node (null for the root)
     */
    private void linkParents(FileSystemNode node, GameDirectory parent) {
        node.setParent(parent);

        // If this node is a directory, do the same for its children
        if (node instanceof GameDirectory dir) {
            for (FileSystemNode child : dir.getChildren().values()) {
                linkParents(child, dir);
            }
        }
    }
}