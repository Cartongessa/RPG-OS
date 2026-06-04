package it.unicam.cs.mpgc.rpg129301.model.objectives;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class DeleteFileObjective implements Objective {
    private final String targetFileName;
    private final GameDirectory targetDirectory;

    public DeleteFileObjective(String targetFileName, GameDirectory targetDirectory) {
        this.targetFileName = targetFileName;
        this.targetDirectory = targetDirectory;
    }

    @Override
    public String getDescription() {
        return "Delete the file '" + targetFileName + "' from the directory '" + targetDirectory.getName() + "'.";
    }

    @Override
    public boolean isCompleted(GameState state) {
        return targetDirectory.getChild(targetFileName) == null;
    }

    @Override
    public String getSuccessMessage() {
        return "\n[SYSTEM]: Log cancelled. Traces removed successfully.\n";
    }
}