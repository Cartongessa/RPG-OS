package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;

public interface GameCommand {
    /**
     * Esegue il comando e restituisce l'output da mostrare nel terminale del gioco.
     * @param args Gli argomenti passati (es. se scrivo "cd logs", args[0] è "logs")
     * @param state Lo stato attuale del gioco (per sapere dove siamo)
     */
    String execute(String[] args, GameState state);

    String getCommandName();

    String getDescription();

}