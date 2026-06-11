package it.unicam.cs.mpgc.rpg129301.model;

public class PlayerStats {

    private int scripting;
    private int problemSolving;
    private int decryption;
    private int hintsUsed;

    public PlayerStats(int scripting, int problemSolving, int decryption) {
        this.scripting = scripting;
        this.problemSolving = problemSolving;
        this.decryption = decryption;
        this.hintsUsed = 0;
    }

    public int getScripting() { return scripting; }
    public void setScripting(int scripting) { this.scripting = scripting; }

    public int getProblemSolving() { return problemSolving; }
    public void setProblemSolving(int problemSolving) { this.problemSolving = problemSolving; }

    public int getDecryption() { return decryption; }
    public void setDecryption(int decryption) { this.decryption = decryption; }


    /**
     * Calculates the maximum number of hints based on the player's Problem-Solving level
     */
    public int getMaxHints() {
        return 1 + (problemSolving / 2);
    }

    /**
     * Returns the number of remaining available hints
     */
    public int getRemainingHints() {
        return Math.max(0, getMaxHints() - hintsUsed);
    }

    /**
     * Use an hint if possible, returns true if an hint was used, false otherwise
     */
    public boolean useHint() {
        if (getRemainingHints() > 0) {
            hintsUsed++;
            return true;
        }
        return false;
    }
}