package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class GameState {
    private GameDirectory currentDirectory;
    private String currentUser;

    public GameState(GameDirectory startDirectory, String startingUser) {
        this.currentDirectory = startDirectory;
        this.currentUser = startingUser;
    }

    public GameDirectory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(GameDirectory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}