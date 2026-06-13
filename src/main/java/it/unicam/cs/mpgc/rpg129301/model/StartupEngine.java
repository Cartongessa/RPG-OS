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
            FileSystemNode homeNode = root.getChild("home");

            // If home directory does not exist, create a fallback state
            if (!(homeNode instanceof GameDirectory homeDir)) {
                return createFallbackState(root);
            }

            FileSystemNode startNode = homeDir.getChild(startAccount);

            // If the directory of the user does not exist, create a fallback state
            if (!(startNode instanceof GameDirectory startingDir)) {
                return createFallbackState(root);
            }

            return new GameState(startingDir, startAccount, levelData.getObjective(), accounts, levelIndex);

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

        parser.register("cat", new CatCommand());
        parser.register("cd", new CdCommand());
        parser.register("exploit", new ExploitCommand());
        parser.register("help", new HelpCommand(parser.getRegisteredCommands()));
        parser.register("hint", new HintCommand());
        parser.register("learn", new LearnCommand());
        parser.register("load", new LoadCommand(this));
        parser.register("login", new LoginCommand());
        parser.register("ls", new LsCommand());
        parser.register("rm", new RmCommand());
        parser.register("save", new SaveCommand());
        parser.register("tutorial", new TutorialCommand());

        return parser;
    }
}