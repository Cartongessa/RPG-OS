package it.unicam.cs.mpgc.rpg129301.utils;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class LevelData {
    private int levelIndex;
    private String startingAccount;
    private GameDirectory fileSystem;

    public int getLevelIndex() { return levelIndex; }
    public String getStartingAccount() { return startingAccount; }
    public GameDirectory getFileSystem() { return fileSystem; }
}