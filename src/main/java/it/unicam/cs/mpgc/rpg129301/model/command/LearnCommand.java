package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.PlayerStats;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;

import java.util.Map;

public class LearnCommand implements GameCommand {

    @Override
    public String execute(String[] args, GameState state) {
        // Ensure the user provided exactly one argument (the filename)
        if (args.length != 1) {
            return "Usage: learn <filename>";
        }

        String targetFileName = args[0];

        // Check if the file exists in the current directory
        FileSystemNode fileNode = state.getCurrentDirectory().getChild(targetFileName);

        if (fileNode == null) {
            return "Error: File '" + targetFileName + "' not found in the current directory.";
        }

        // Extract the PlayerStats from the current GameState
        PlayerStats stats = state.getPlayerStats();

        // Check if the filename contains a skill name
        String learnedSkill = findSkillInFileName(targetFileName, stats);

        // If a skill is found, increase it and notify the player
        if (learnedSkill != null) {
            stats.increaseSkill(learnedSkill, 1);
            return "Success! You have analyzed '" + targetFileName + "' and improved your '" + learnedSkill + "' skill.";
        } else {
            return "You analyzed '" + targetFileName + "', but found no useful skills to learn.";
        }
    }

    @Override
    public String getCommandName() {
        return "learn";
    }

    @Override
    public String getCommandUsage() {
        return " <filename>";
    }

    @Override
    public String getDescription() {
        return "Allows the agent to learn a new skill by analyzing a specific file.";
    }

    @Override
    public String getBriefDescription() {
        return "Learns a new skill based on the filename.";
    }

    /**
     * Checks if the given file name contains any of the registered skills.
     * Returns the name of the skill if found, or null if no skill is found.
     * * @param fileName The name of the file being analyzed
     * @param stats The player's current stats to retrieve the list of available skills
     */
    public String findSkillInFileName(String fileName, PlayerStats stats) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // Retrieve the skills map dynamically from the passed PlayerStats
        Map<String, Integer> skillsMap = stats.getSkills();

        // Checks all the skills in the map
        for (String skill : skillsMap.keySet()) {
            // If the file name contains the skill name, we consider it a match (case-insensitive)
            if (fileName.toLowerCase().contains(skill)) {
                return skill;
            }
        }

        return null;
    }
}