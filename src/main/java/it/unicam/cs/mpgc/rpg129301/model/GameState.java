package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.objectives.Objective;
import java.util.Map;

public class GameState {
    private GameDirectory currentDirectory;
    private String currentUser;
    private Objective currentObjective;
    private Map<String, String> accounts;

    // Fields used for saving feature
    private int levelIndex;
    private String currentLog;

    public GameState(GameDirectory startDirectory, String startingUser, Objective currentObjective, Map<String, String> accounts, int levelIndex) {
        this.currentDirectory = startDirectory;
        this.currentUser = startingUser;
        this.currentObjective = currentObjective;
        this.accounts = accounts;
        this.levelIndex = levelIndex;
        this.currentLog = "";
    }

    public int getLevelIndex() { return levelIndex; }
    public void setLevelIndex(int levelIndex) { this.levelIndex = levelIndex; }

    public String getCurrentLog() { return currentLog; }
    public void setCurrentLog(String currentLog) { this.currentLog = currentLog; }

    public Map<String, String> getAccounts() {
        return accounts;
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
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Objective getObjective() {
        return currentObjective;
    }
    public void setObjective(Objective currentObjective) {
        this.currentObjective = currentObjective;
    }
}