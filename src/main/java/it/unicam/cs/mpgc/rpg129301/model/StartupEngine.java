package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.command.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.utils.LevelLoader;
import it.unicam.cs.mpgc.rpg129301.utils.LevelData;

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

        LevelData levelData = loader.loadLevel(levelIndex);

        GameDirectory root = levelData.getFileSystem();
        String startAccount = levelData.getStartingAccount();

        try {
            FileSystemNode homeNode = root.getChild("home");

            if (homeNode instanceof GameDirectory homeDir) {
                FileSystemNode startNode = homeDir.getChild(startAccount);

                if (startNode instanceof GameDirectory startingDir) {
                    return new GameState(startingDir, startAccount);
                }
            }

            // If user directory is not found, start from root as "root"
            return new GameState(root, "root");

        } catch (Exception e) {
            System.err.println("Error while setting up the starting directory: " + e.getMessage());
            return new GameState(root, "root");
        }
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
        // Future commands like "hint" will be registered here
        return parser;
    }
}