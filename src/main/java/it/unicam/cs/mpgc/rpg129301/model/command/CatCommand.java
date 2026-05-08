package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class CatCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {
        if (args.length == 0) {
            return "Error: specify the name of a file.";
        }

        String targetName = args[0];

        GameDirectory currentDir = state.getCurrentDirectory();
        FileSystemNode targetNode = currentDir.getChild(targetName);

        if (targetNode == null) {
            return "Error: no file found as '" + targetName + "'.";
        } else if (targetNode instanceof GameDirectory) {
            return "Error: '" + targetName + "' is a directory, not a file.";
        } else {
            return ((GameFile) targetNode).getContent();
        }
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public String getCommandUsage() {
        return " [file]";
    }

    @Override
    public String getDescription() {
        return "Shows the content of a file.";
    }

    @Override
    public String getBriefDescription() {
        return "Shows the content of a file.";
    }
}
