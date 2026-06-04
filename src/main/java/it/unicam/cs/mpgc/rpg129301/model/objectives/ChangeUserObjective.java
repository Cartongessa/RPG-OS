package it.unicam.cs.mpgc.rpg129301.model.objectives;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Objects;

public class ChangeUserObjective implements Objective {
    private final String targetUser;

    public ChangeUserObjective(String targetUser) {
        this.targetUser = targetUser;
    }

    @Override
    public String getDescription() {
        return "Gain access as user '" + targetUser + "'.";
    }

    @Override
    public boolean isCompleted(GameState state) {
        return state != null && Objects.equals(state.getCurrentUser(), targetUser);
    }

    @Override
    public String getSuccessMessage() {
        return "You are now '" + targetUser + "'.";
    }
}

