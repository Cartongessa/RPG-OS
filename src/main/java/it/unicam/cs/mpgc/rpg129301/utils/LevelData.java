package it.unicam.cs.mpgc.rpg129301.utils;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.objectives.Objective;

import java.util.Map;

public class LevelData {
    private int levelIndex;
    private String startingAccount;
    private GameDirectory fileSystem;
    private Objective objective;
    private Map<String, String> accounts;

    public int getLevelIndex() { return levelIndex; }
    public String getStartingAccount() { return startingAccount; }
    public GameDirectory getFileSystem() { return fileSystem; }
    public Objective getObjective() { return objective; }
    public Map<String, String> getAccounts() {
        return accounts;
    }
}