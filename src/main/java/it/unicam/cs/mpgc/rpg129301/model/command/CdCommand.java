package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class CdCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {

        // If there is not any argument
        if (args.length == 0) {
            return "Error: specify the name of the directory (or '..').";
        }

        String targetName = args[0];
        GameDirectory currentDir = state.getCurrentDirectory();

        // If the target is '..', move to the parent directory if it exists, otherwise return an error message
        if (targetName.equals("..")) {
            if (currentDir.getParent() != null) {
                state.setCurrentDirectory(currentDir.getParent());
                return "";
            } else {
                return "Error: you already are in root (/).";
            }
        }

        FileSystemNode targetNode = currentDir.getChild(targetName);

        // If the specified node is a directory, change the current directory to it, otherwise return an error message
        if (targetNode instanceof GameDirectory) {
            state.setCurrentDirectory((GameDirectory) targetNode);
            return "";
        } else if (targetNode != null) {
            return "Error: '" + targetName + "' is a file, not a directory.";
        } else {
            return "Error: no directory found as '" + targetName + "'.";
        }
    }

    @Override
    public String getCommandName() {
        return "cd";
    }

    @Override
    public String getCommandUsage() {return " [dir]";}

    @Override
    public String getDescription() {
        return "Changes current directory.";
    }

    @Override
    public String getBriefDescription() {
        return "Changes current directory.";
    }
}
