package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class RmCommand implements GameCommand {

    @Override
    public String execute(String[] args, GameState state) {

        // If no arguments are provided, return an error message
        if (args.length == 0) {
            return "[ERROR]: Usage: rm [filename]";
        }

        String targetFileName = args[0];

        FileSystemNode targetNode = state.getCurrentDirectory().getChild(targetFileName);

        if (targetNode == null) {
            return "[ERROR]: rm: cannot remove '" + targetFileName + "': No such file";
        }
        if (!(targetNode instanceof GameFile)) {
            return "[ERROR]: rm: cannot remove '" + targetFileName + "': Is a directory";
        }

        // Delete the file
        state.getCurrentDirectory().removeChild(targetFileName);

        return "File '" + targetFileName + "' removed.";
    }

    @Override
    public String getCommandName() { return "rm"; }

    @Override
    public String getCommandUsage() { return " [filename]"; }

    @Override
    public String getDescription() { return "Removes a file from the current directory."; }

    @Override
    public String getBriefDescription() { return "Deletes a file"; }
}