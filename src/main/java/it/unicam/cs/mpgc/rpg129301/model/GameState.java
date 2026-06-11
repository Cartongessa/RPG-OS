package it.unicam.cs.mpgc.rpg129301.model;

import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.objectives.Objective;
import java.util.Map;

public class GameState {
    // Fields used for saving file system data
    private GameDirectory currentDirectory;
    private String currentUser;
    private Objective currentObjective;
    private Map<String, String> accounts;

    // Fields used for saving feature
    private int levelIndex;
    private String currentLog;

    // Field used for saving player stats
    private PlayerStats playerStats;

    // Field used for saving trace level
    private int traceLevel;

    // Maximum trace level before game over
    private final int MAX_TRACE_LEVEL = 100;

    public GameState(GameDirectory startDirectory, String startingUser, Objective currentObjective, Map<String, String> accounts, int levelIndex) {
        this.currentDirectory = startDirectory;
        this.currentUser = startingUser;
        this.currentObjective = currentObjective;
        this.accounts = accounts;
        this.levelIndex = levelIndex;
        this.currentLog = "";
        this.playerStats = new PlayerStats(0, 0, 0);
        this.traceLevel = 0;
    }

    // Getters and Setters

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

    public PlayerStats getPlayerStats() {
        return this.playerStats;
    }
    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    public int getTraceLevel() {
        return traceLevel;
    }
    public void setTraceLevel(int traceLevel) {
        this.traceLevel = traceLevel;
    }

    /**
     * Increments trace level by a specified amount
     */
    public void incrementTraceLevel(int amount) {
        this.traceLevel += amount;
        if (this.traceLevel > 100) {
            this.traceLevel = 100;
        }

        // Check if the trace level has reached the game over condition after incrementing
        if (isGameOverByTrace()) {
            System.out.println("\n[ALERT] The intrusion detection system has blocked you. ACCESS DENIED");
        }
    }

    /**
     * Reduces trace level by a specified amount (i.e. when the player clears the command history)
     */
    public void decrementTraceLevel(int amount) {
        this.traceLevel = Math.max(0, this.traceLevel - amount);
    }

    /**
     * Verifies if the trace level has reached the maximum level
     */
    public boolean isGameOverByTrace() {
        return this.traceLevel >= MAX_TRACE_LEVEL;
    }
}