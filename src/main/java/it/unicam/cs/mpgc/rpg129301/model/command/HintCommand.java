package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.PlayerStats;

public class HintCommand implements GameCommand {

    @Override
    public String execute(String[] args, GameState state) {

        PlayerStats stats = state.getPlayerStats();

        if (stats != null) {
            if (stats.useHint()) {
                // Checks how many hints are left to be used
                int remainingHints = stats.getRemainingHints();
                return "[HINT] (" + remainingHints  + " left for this level): ";
            } else {
                return "[ERROR] You finished all the hints for this level, increase Problem Solving to get more.";
            }
        }

        // Placeholder hint
        return "PLACEHOLDER HINT: The file has to be somewhere around here, but I can't see it...";
    }

    @Override
    public String getCommandName() {
        return "hint";
    }

    @Override
    public String getCommandUsage() {
        return "hint";
    }

    @Override
    public String getDescription() {
        return "Provides an hint for the current level. The number of usages depends on the Problem Solving level";
    }

    @Override
    public String getBriefDescription() {
        return "Provides an hint for the current level";
    }
}