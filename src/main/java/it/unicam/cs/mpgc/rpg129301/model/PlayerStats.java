package it.unicam.cs.mpgc.rpg129301.model;

import java.util.Map;
import java.util.HashMap;

public class PlayerStats {

    private int hintsUsed;

    private Map<String, Integer> skills = new HashMap<>();

    public PlayerStats() {
        skills.put("scripting", 1);
        skills.put("decryption", 0);
        skills.put("problem_solving", 0);
    }

    /**
     * Retrieves a skill level dynamically. Returns 0 if the skill doesn't exist.
     */
    public int getSkillLevel(String skillName) {
        return skills.getOrDefault(skillName.toLowerCase(), 0);
    }

    /**
     * Increases a specific skill dynamically.
     */
    public void increaseSkill(String skillName, int amount) {
        String key = skillName.toLowerCase();
        int currentLevel = getSkillLevel(key);
        skills.put(key, currentLevel + amount);
    }

    /**
     * Calculates the maximum number of hints based on the player's Problem-Solving level
     */
    public int getMaxHints() {
        return 1 + (getSkillLevel("problem_solving") / 2);
    }

    /**
     * Returns the number of remaining available hints
     */
    public int getRemainingHints() {
        return Math.max(0, getMaxHints() - hintsUsed);
    }

    /**
     * Use a hint if possible, returns true if a hint was used, false otherwise
     */
    public boolean useHint() {
        if (getRemainingHints() > 0) {
            hintsUsed++;
            return true;
        }
        return false;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }
}