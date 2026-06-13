package it.unicam.cs.mpgc.rpg129301.model.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129301.model.GameState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveCommand implements GameCommand {

    // Constants for the save directory and file extension
    public static final String SAVE_DIRECTORY = "saves";
    public static final String FILE_EXTENSION = ".json";

    @Override
    public String execute(String[] args, GameState state) {
        // If the user wants to set a name for the save file
        String fileName = args.length > 0 ? (args[0] + FILE_EXTENSION) : "savegame"+FILE_EXTENSION;

        // Define the directory and the full file path
        File directory = new File(SAVE_DIRECTORY);

        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdir();
        }

        File saveFile = new File(directory, fileName);

        // Map data to be saved in JSON
        Map<String, Object> saveData = new HashMap<>();
        saveData.put("levelIndex", state.getLevelIndex());
        saveData.put("currentPosition", state.getCurrentDirectory().getName());
        saveData.put("log", state.getCurrentLog());
        saveData.put("traceLevel", state.getTraceLevel());
        saveData.put("playerStats", state.getPlayerStats());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(saveFile)) {
            gson.toJson(saveData, writer);
            return "[SYSTEM]: Game saved successfully in file '" + saveFile.getPath() + "'.";
        } catch (IOException e) {
            return "[ERROR]: It was not possible to save data. Further details: " + e.getMessage();
        }
    }

    @Override
    public String getCommandName() { return "save"; }

    @Override
    public String getCommandUsage() { return " [filename]"; }

    @Override
    public String getDescription() { return "Saves the actual game in a file"; }

    @Override
    public String getBriefDescription() { return "Saves actual game progresses"; }
}