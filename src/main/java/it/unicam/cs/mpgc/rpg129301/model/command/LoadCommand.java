package it.unicam.cs.mpgc.rpg129301.model.command;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.PlayerStats;
import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

import java.io.File;
import java.io.FileReader;

import static it.unicam.cs.mpgc.rpg129301.model.command.SaveCommand.FILE_EXTENSION;
import static it.unicam.cs.mpgc.rpg129301.model.command.SaveCommand.SAVE_DIRECTORY;

public class LoadCommand implements GameCommand {

    private final StartupEngine engine; // <-- NEW: Dependency Injection

    // Constructor to receive the engine
    public LoadCommand(StartupEngine engine) {
        this.engine = engine;
    }

    // Helper class that represents JSON structure
    private static class SaveGameData {
        int levelIndex;
        String currentPosition;
        String log;
        int traceLevel;
        PlayerStats playerStats;
        String currentUser;
    }

    @Override
    public String execute(String[] args, GameState state) {
        String fileName = args.length > 0 ? args[0] : "savegame" + FILE_EXTENSION;

        if (args.length > 0 && !fileName.endsWith(FILE_EXTENSION)) {
            fileName += FILE_EXTENSION;
        }

        File saveFile = new File(SAVE_DIRECTORY, fileName);

        if (!saveFile.exists()) {
            return "[ERROR]: Save file '" + fileName + "' does not exist.";
        }

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(saveFile)) {
            SaveGameData loadedData = gson.fromJson(reader, SaveGameData.class);

            // Build a completely fresh state for the saved level using the JSON
            GameState freshState = engine.setupGame(loadedData.levelIndex);

            // Replace the Ghost State with the fresh Level data
            state.setLevelIndex(freshState.getLevelIndex());
            state.setCurrentUser(freshState.getCurrentUser());
            state.setObjective(freshState.getObjective());
            state.setCurrentDirectory(freshState.getCurrentDirectory());

            // Reload, if present, the saved user
            if (loadedData.currentUser != null && !loadedData.currentUser.isEmpty()) {
                state.setCurrentUser(loadedData.currentUser);
            } else {
                // Fallback to the default user of that level if it wasn't saved
                state.setCurrentUser(freshState.getCurrentUser());
            }

            // inject the player's saved progress
            state.setCurrentLog(loadedData.log);
            state.setTraceLevel(Math.max(0, loadedData.traceLevel));

            if (loadedData.playerStats != null) {
                state.setPlayerStats(loadedData.playerStats);
            }

            // Resolve the directory (Attempt to place player in their saved folder)
            GameDirectory rootDir = state.getCurrentDirectory().getRoot();
            GameDirectory targetDirectory = rootDir.findDirectoryByName(loadedData.currentPosition);

            if (targetDirectory != null) {
                state.setCurrentDirectory(targetDirectory);
            } else {
                // Safe fallback to root if the directory no longer exists
                state.setCurrentDirectory(rootDir);
            }

            return "[LOAD_SUCCESS]";

        } catch (Exception e) {
            return "[ERROR]: Could not load the game. Details: " + e.getMessage();
        }
    }

    @Override
    public String getCommandName() { return "load"; }

    @Override
    public String getCommandUsage() { return "load [filename]"; }

    @Override
    public String getDescription() { return "Loads a previously saved game state."; }

    @Override
    public String getBriefDescription() { return "Loads the game."; }
}