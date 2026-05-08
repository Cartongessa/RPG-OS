package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.command.*;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.utils.LevelLoader;

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
        GameDirectory root = loader.loadLevel(levelIndex);

        try {
            // 1. Get 'home' as a generic node
            FileSystemNode homeNode = root.getChild("home");

            // 2. Cast it to GameDirectory so we can use .getChild() again
            if (homeNode instanceof GameDirectory homeDir) {
                GameDirectory guest = (GameDirectory) homeDir.getChild("guest");
                return new GameState(guest);
            }

            return new GameState(root);
        } catch (Exception e) {
            // Fallback to root if navigation fails
            return new GameState(root);
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