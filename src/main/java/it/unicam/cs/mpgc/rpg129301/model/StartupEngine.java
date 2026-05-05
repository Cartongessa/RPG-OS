package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.command.CatCommand;
import it.unicam.cs.mpgc.rpg129301.model.command.CdCommand;
import it.unicam.cs.mpgc.rpg129301.model.command.CommandParser;
import it.unicam.cs.mpgc.rpg129301.model.command.LsCommand;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class StartupEngine {

    public GameDirectory fileSystemSetup() {
        // --- FileSystem setup, temporarily static ---
        GameDirectory root = new GameDirectory("/", null);
        GameDirectory homeDir = new GameDirectory("home", root);
        GameDirectory guestDir = new GameDirectory("guest", homeDir);
        GameDirectory binDir = new GameDirectory("bin", root);

        GameDirectory startDir = guestDir;

        root.addChild(homeDir);
        root.addChild(binDir);
        homeDir.addChild(guestDir);

        // Adding level files
        guestDir.addChild(new GameFile("readme.txt", "Storia inutile del gioco...", false));
        guestDir.addChild(new GameFile("passwords_backup", "user_target_pwd_123", true));

        return startDir;
    }

    public CommandParser parserSetup() {
        CommandParser parser = new CommandParser();

        parser.register("ls", new LsCommand());
        parser.register("cd", new CdCommand());
        parser.register("cat", new CatCommand());

        return parser;
    }


}
