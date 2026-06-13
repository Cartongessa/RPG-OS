package it.unicam.cs.mpgc.rpg129301.model.command;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.PlayerStats;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

import java.io.File;
import java.io.FileReader;

import static it.unicam.cs.mpgc.rpg129301.model.command.SaveCommand.FILE_EXTENSION;
import static it.unicam.cs.mpgc.rpg129301.model.command.SaveCommand.SAVE_DIRECTORY;

public class LoadCommand implements GameCommand {

    // Helper class that represents JSON structure
    private static class SaveGameData {
        int levelIndex;
        String currentPosition;
        String log;
        int traceLevel;
        PlayerStats playerStats;
    }

    @Override
    public String execute(String[] args, GameState state) {
        // If no arguments are provided, return an error message
        String fileName = args.length > 0 ? args[0] : "savegame" + FILE_EXTENSION;

        // If the provided filename does not end with the defined file extension, append it
        if (args.length > 0 && !fileName.endsWith(FILE_EXTENSION)) {
            fileName += FILE_EXTENSION;
        }

        // Identify the input as a file in the "saves" directory
        File saveFile = new File(SAVE_DIRECTORY, fileName);

        // If the file does not exist, return an error message
        if (!saveFile.exists()) {
            return "[ERROR]: Save file '" + fileName + "' does not exist.";
        }

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(saveFile)) {
            // Create a variable with all the data from the JSON file
            SaveGameData loadedData = gson.fromJson(reader, SaveGameData.class);

            // Set the state to the parameters found in the file
            state.setLevelIndex(loadedData.levelIndex);
            state.setCurrentLog(loadedData.log);

            // Load the RPG metrics into the current state
            state.setTraceLevel(Math.max(0, loadedData.traceLevel));

            if (loadedData.playerStats != null) {
                state.setPlayerStats(loadedData.playerStats);
            }

            // Resolve the directory
            GameDirectory currentDir = state.getCurrentDirectory();
            GameDirectory targetDirectory = currentDir.getRoot().findDirectoryByName(loadedData.currentPosition);

            // If the directory exists, set it as the current directory, otherwise return an error message
            if (targetDirectory != null) {
                state.setCurrentDirectory(targetDirectory);
            } else {
                return "[ERROR]: Directory '" + loadedData.currentPosition + "' not found in the file system.";
            }

            // This flag is caught by TerminalController to trigger a full UI refresh
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
    public String getDescription() { return "Loads a previously saved game state."; }

    @Override
    public String getBriefDescription() { return "Loads the game."; }
}