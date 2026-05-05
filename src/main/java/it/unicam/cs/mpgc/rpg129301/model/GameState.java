package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class GameState {
    private GameDirectory currentDirectory;

    public GameState(GameDirectory startDirectory) {
        this.currentDirectory = startDirectory;
    }

    public GameDirectory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(GameDirectory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
}