package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class LsCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {

        // Check if the -a flag is present to show hidden files
        boolean showHidden = args.length > 0 && args[0].equals("-a");
        StringBuilder output = new StringBuilder();

        for (FileSystemNode child : state.getCurrentDirectory().getChildren().values()) {

            boolean isHidden = (child instanceof GameFile) && ((GameFile) child).isHidden();

            if (showHidden || !isHidden) {
                // Add a dot prefix for hidden files to visually distinguish them
                String displayName = child.getName();

                // Add a type prefix to indicate if it's a directory or a file
                String typePrefix = (child instanceof GameDirectory) ? "[DIR]  " : "[FILE] ";
                output.append(typePrefix).append(displayName).append("\n");
            }
        }

        return output.toString().trim();
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public String getCommandUsage() {
        return " [-a]";
    }

    @Override
    public String getDescription() {
        return "Lists files in the current directory. \n" +
                "Use -a to show hidden files.";
    }

    @Override
    public String getBriefDescription() {
        return "Lists files in the current directory.";
    }
}