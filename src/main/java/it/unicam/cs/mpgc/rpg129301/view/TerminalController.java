package it.unicam.cs.mpgc.rpg129301.view;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.model.command.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TerminalController {

    // Terminal UI Components
    @FXML private TextArea terminalOutput;
    @FXML private TextField commandInput;

    // RPG Sidebar UI Components
    @FXML private ProgressBar traceProgressBar;
    @FXML private Label traceLabel;
    @FXML private Label scriptingLabel;
    @FXML private Label problemSolvingLabel;
    @FXML private Label decryptionLabel;

    private GameState state;
    private CommandParser parser;
    private SidebarManager sidebarManager;
    private StartupEngine engine;

    @FXML
    public void initialize() {
        // Initialize the engine and save it to the class variable
        this.engine = new StartupEngine();

        // Set up the game state and command parser
        this.state = engine.setupGame(1);
        this.parser = engine.setupParser();

        // Initialize the SidebarManager
        this.sidebarManager = new SidebarManager(
                traceProgressBar, traceLabel, scriptingLabel, problemSolvingLabel, decryptionLabel
        );

        terminalOutput.appendText("=== RPG-OS TERMINAL v2.0 ===\n");
        terminalOutput.appendText("Type 'exit' to terminate the session.\n\n");

        sidebarManager.update(this.state);
        terminalOutput.appendText(buildPrompt());
    }

    @FXML
    public void handleCommand() {
        String input = commandInput.getText().trim();

        if (input.isEmpty()) return;
        commandInput.clear();

        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        printToTerminal(input);

        String response = parser.process(input, state);
        handleCommandResponse(response);

        // Check if the objective has been reached
        checkObjective();

        // Log state and prepare for next input
        endTurn();
    }

    private void printToTerminal(String text) {
        terminalOutput.appendText(text + "\n\n");
    }

    private void handleCommandResponse(String response) {
        // If the player just used load command (if the special flag is set)
        if ("[LOAD_SUCCESS]".equals(response)) {
            terminalOutput.setText(state.getCurrentLog());
            printToTerminal("\n[SYSTEM]: Game loaded successfully.");
        } else if (response != null && !response.isEmpty()) {
            printToTerminal(response);
        }
    }

    /**
     * Checks if the current objective is met and triggers the next level
     */
    private void checkObjective() {
        if (state.getObjective() != null && state.getObjective().isCompleted(state)) {

            // Print the success banner
            printToTerminal("=========================================\n" +
                    "[MISSION ACCOMPLISHED]: " + state.getObjective().getSuccessMessage() + "\n" +
                    "=========================================");

            // Calculate the next level and load it
            int nextLevelIndex = state.getLevelIndex() + 1;
            loadNextLevel(nextLevelIndex);
        }
    }

    /**
     * Clears the terminal, loads the next level JSON, and transfers player stats.
     */
    private void loadNextLevel(int nextLevelIndex) {
        try {
            // Temporarily save the player's stats so they don't reset
            var savedStats = state.getPlayerStats();

            // Load the new level
            this.state = engine.setupGame(nextLevelIndex);

            // Transfer the stats to the new level and reset the trace level to 0
            this.state.setPlayerStats(savedStats);
            this.state.setTraceLevel(0);

            // Clear the screen for the new level
            terminalOutput.clear();
            terminalOutput.appendText("=== CONNECTION ESTABLISHED: LEVEL " + nextLevelIndex + " ===\n");

            if (state.getCurrentLog() != null && !state.getCurrentLog().isEmpty()) {
                terminalOutput.appendText(state.getCurrentLog() + "\n\n");
            }

        } catch (Exception e) {

            e.printStackTrace();
            // If setupGame fails (e.g., level3.json doesn't exist yet), they beat the game!
            printToTerminal("[SYSTEM]: No further connection nodes found.");
            printToTerminal("YOU HAVE SUCCESSFULLY HACKED THE MAINFRAME. GAME OVER.");
        }
    }

    private void endTurn() {
        state.setCurrentLog(terminalOutput.getText());
        sidebarManager.update(state);
        terminalOutput.appendText(buildPrompt());
    }

    private String buildPrompt() {
        return state.getCurrentUser() + "@rpg-os:" + state.getCurrentDirectory().getName() + "$ ";
    }
}