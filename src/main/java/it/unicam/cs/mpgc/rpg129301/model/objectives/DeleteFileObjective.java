package it.unicam.cs.mpgc.rpg129301.model.objectives;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class DeleteFileObjective implements Objective {
    private final String targetFileName;
    private final String targetDirectoryName;

    public DeleteFileObjective(String targetFileName, String targetDirectoryName) {
        this.targetFileName = targetFileName;
        this.targetDirectoryName = targetDirectoryName;
    }

    @Override
    public String getDescription() {
        return "Delete the file '" + targetFileName + "' from the directory '" + targetDirectoryName + "'.";
    }

    @Override
    public boolean isCompleted(GameState state) {
        // Get the root of the file system
        GameDirectory root = state.getCurrentDirectory().getRoot();

        // Find the target directory dynamically
        GameDirectory targetDir = root.findDirectoryByName(targetDirectoryName);

        // If the directory exists, check if the file is missing (deleted)
        if (targetDir != null) {
            return targetDir.getChild(targetFileName) == null;
        }

        return false;
    }

    @Override
    public String getSuccessMessage() {
        return "\n[SYSTEM]: Log cancelled. Traces removed successfully.\n";
    }
}