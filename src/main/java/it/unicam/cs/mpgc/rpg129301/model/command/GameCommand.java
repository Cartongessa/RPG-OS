package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;

public interface GameCommand {
    /**
     * Executes the command and returns the output to be shown in the game's terminal
     * @param args The arguments passed to the command (e.g. for "ls -a", args would be ["-a"])
     * @param state The current state of the game
     */
    String execute(String[] args, GameState state);

    String getCommandName();

    String getCommandUsage();

    String getDescription();

    String getBriefDescription();

}