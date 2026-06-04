package it.unicam.cs.mpgc.rpg129301.model.objectives;

import it.unicam.cs.mpgc.rpg129301.model.GameState;

public interface Objective {
    // Description of the objective
    String getDescription();

    // Returns true when the objective is satisfied given the current game state
    boolean isCompleted(GameState state);

    // Returns a string assessing the reached objective
    String getSuccessMessage();
}
