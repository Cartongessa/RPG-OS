package it.unicam.cs.mpgc.rpg129301.model.command;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

import java.io.File;
import java.io.FileReader;

public class LoadCommand implements GameCommand {

    // Helper class that represents JSON structure
    private static class SaveGameData {
        int levelIndex;
        String currentPosition;
        String log;
    }

    @Override
    public String execute(String[] args, GameState state) {
        String fileName = args.length > 0 ? args[0] : "savegame.json";
        if (args.length > 0 && !fileName.endsWith(".json")) {
            fileName += ".json";
        }

        // Identify the input as a file
        File saveFile = new File("saves", fileName);

        if (!saveFile.exists()) {
            return "[ERROR]: Save file '" + fileName + "' does not exist.";
        }

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(saveFile)) {
            SaveGameData loadedData = gson.fromJson(reader, SaveGameData.class);

            state.setLevelIndex(loadedData.levelIndex);
            state.setCurrentLog(loadedData.log);

            // Resolve the directory
            GameDirectory currentDir = state.getCurrentDirectory();
            GameDirectory targetDirectory = currentDir.getRoot().findDirectoryByName(loadedData.currentPosition);

            if (targetDirectory != null) {
                state.setCurrentDirectory(targetDirectory);
            } else {
                return "[ERROR]: Directory '" + loadedData.currentPosition + "' not found in the file system.";
            }

            return "[LOAD_SUCCESS]";

        } catch (Exception e) {
            return "[ERROR]: Could not load the game. Details: " + e.getMessage();
        }
    }

    @Override
    public String getCommandName() { return "load"; }

    @Override
    public String getCommandUsage() { return " [filename]"; }

    @Override
    public String getDescription() { return "Loads a previously saved game state"; }

    @Override
    public String getBriefDescription() { return "Loads the game"; }
}