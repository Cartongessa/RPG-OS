package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.command.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.utils.LevelLoader;
import it.unicam.cs.mpgc.rpg129301.utils.LevelData;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the initial setup of the game environment
 */
public class StartupEngine {
    /**
     * Loads the FileSystem from a JSON file and prepares the GameState
     * @param levelIndex The index of the level
     * @return A fully initialized GameState
     */
    public GameState setupGame(int levelIndex) {
        LevelLoader loader = new LevelLoader();

        // Loads a level by the index
        LevelData levelData = loader.loadLevel(levelIndex);

        // Loads the root and the starting account
        GameDirectory root = levelData.getFileSystem();
        String startAccount = levelData.getStartingAccount();

        // Loads all the accounts for the current level
        Map<String, String> accounts = levelData.getAccounts();

        try {
            // Navigate to the starting directory based on the provided starting account
            FileSystemNode homeNode = root.getChild("home");
            // If the node is a directory
            if (homeNode instanceof GameDirectory homeDir) {
                // Find the starting directory by looking for the directory named as the starting account
                FileSystemNode startNode = homeDir.getChild(startAccount);
                // If the starting node is a directory, initialize the GameState with it
                if (startNode instanceof GameDirectory startingDir) {
                    return new GameState(startingDir, startAccount, levelData.getObjective(), accounts, levelIndex);
                }
            }
            return createFallbackState(root);
        } catch (Exception e) {
            System.err.println("Error while setting up the starting directory: " + e.getMessage());
            return createFallbackState(root);
        }
    }

    /**
     * Creates an emergency state in case loading fails
     * @return A GameState with the root directory as the starting point, a single account "root" with password "admin", and no objective
     */
    private GameState createFallbackState(GameDirectory root) {
        Map<String, String> fallbackAccounts = new HashMap<>();
        fallbackAccounts.put("root", "admin");
        // The fallback state will have the root directory as the starting point, with a single account "root" with password "admin", and won't have an objective
        return new GameState(root, "root", null, fallbackAccounts, 1);
    }

    /**
     * Registers all available commands into the CommandParser
     * @return A configured CommandParser
     */
    public CommandParser setupParser() {
        CommandParser parser = new CommandParser();

        parser.register("ls", new LsCommand());
        parser.register("cd", new CdCommand());
        parser.register("cat", new CatCommand());
        parser.register("help", new HelpCommand(parser.getRegisteredCommands()));
        parser.register("login", new LoginCommand());
        parser.register("save", new SaveCommand());
        parser.register("load", new LoadCommand());
        parser.register("hint", new HintCommand());
        parser.register("exploit", new ExploitCommand());
        parser.register("rm", new RmCommand());

        // Future commands like "hint" will be registered here
        return parser;
    }
}