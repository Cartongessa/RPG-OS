package it.unicam.cs.mpgc.rpg129301.view;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.PlayerStats;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Manages and updates the RPG Sidebar UI components
 */
public class SidebarManager {

    private final ProgressBar traceProgressBar;
    private final Label traceLabel;
    private final Label scriptingLabel;
    private final Label problemSolvingLabel;
    private final Label decryptionLabel;

    /**
     * Constructs the manager by binding the FXML UI components
     */
    public SidebarManager(ProgressBar traceProgressBar, Label traceLabel, Label scriptingLabel, Label problemSolvingLabel, Label decryptionLabel) {
        this.traceProgressBar = traceProgressBar;
        this.traceLabel = traceLabel;
        this.scriptingLabel = scriptingLabel;
        this.problemSolvingLabel = problemSolvingLabel;
        this.decryptionLabel = decryptionLabel;
    }

    /**
     * Synchronizes the sidebar graphical elements with the current GameState
     * @param state The current state of the game containing trace and player stats
     */
    public void update(GameState state) {
        if (state == null) {
            return;
        }

        // Update the Trace Level progress bar and text
        // Converts 0-100 integer range to a 0.0-1.0 double scale for JavaFX ProgressBar
        double progressValue = state.getTraceLevel() / 100.0;
        traceProgressBar.setProgress(progressValue);
        traceLabel.setText(state.getTraceLevel() + "%");

        // Update the Character Skill Labels
        PlayerStats stats = state.getPlayerStats();
        if (stats != null) {
            scriptingLabel.setText(String.valueOf(stats.getSkillLevel("scripting")));
            problemSolvingLabel.setText(String.valueOf(stats.getSkillLevel("problem_solving")));
            decryptionLabel.setText(String.valueOf(stats.getSkillLevel("decryption")));
        }
    }
}