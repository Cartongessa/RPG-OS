//package it.unicam.cs.mpgc.rpg129301.view;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//
//public class TerminalController {
//    @FXML private TextArea terminalOutput;
//    @FXML private TextField commandInput;
//
//    @FXML
//    public void handleCommand() {
//        String input = commandInput.getText();
//        terminalOutput.appendText("> " + input + "\n");
//        commandInput.clear();
//    }
//
//
//
//
//}

package it.unicam.cs.mpgc.rpg129301.view;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.model.command.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TerminalController {
    @FXML private TextArea terminalOutput;
    @FXML private TextField commandInput;

    private GameState state;
    private CommandParser parser;

    /**
     * Initializes the terminal by setting up the game state and command parser, and displays the initial prompt
     */
    @FXML
    public void initialize() {
        StartupEngine engine = new StartupEngine();

        // Set up the game state and command parser
        GameState state = engine.setupGame(1);
        CommandParser parser = engine.setupParser();

        // Print the title
        terminalOutput.appendText("=== RPG-OS TERMINAL v2.0 ===\n");
        terminalOutput.appendText("Type 'exit' to terminate the session.\n\n");

        this.state = state;
        this.parser = parser;

        // Print the first prompt
        terminalOutput.appendText(buildPrompt());
    }

    /**
     * Handles the command entered by the user, processes it through the CommandParser, and updates the terminal output
     */
    @FXML
    public void handleCommand() {

        String input = commandInput.getText().trim();

        if (input.isEmpty()) return;

        commandInput.clear();

        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        // Show the user's input
        printToTerminal(input);

        // Process the command
        String response = parser.process(input, state);

        // Handle response
        handleCommandResponse(response);

        // Post-command check for objectives
        checkObjectives();

        // Log state and prepare for next input
        endTurn();
    }

    /**
     * Appends text to the terminal UI, ensuring that each new message starts on a new line
     */
    private void printToTerminal(String text) {
        terminalOutput.appendText(text + "\n");
    }

    /**
     * Handles text responses and special system flags (like "[LOAD_SUCCESS]")
     */
    private void handleCommandResponse(String response) {
        // If the response is the special flag [LOAD_SUCCESS], refresh the terminal
        if ("[LOAD_SUCCESS]".equals(response)) {
            terminalOutput.setText(state.getCurrentLog());
            printToTerminal("\n[SYSTEM]: Game loaded successfully.");
        } else if (response != null && !response.isEmpty()) {
            printToTerminal(response);
        }
    }

    /**
     * Checks if the current objective is met and notifies the user if it is completed
     */
    private void checkObjectives() {
        if (state.getObjective() != null && state.getObjective().isCompleted(state)) {
            printToTerminal("\n[SYSTEM]: Objective completed! " + state.getObjective().getSuccessMessage());
        }
    }

    /**
     * Syncs the game state and prints the next prompt.
     */
    private void endTurn() {
        // Sync the log in the state so manual saves will capture everything
        state.setCurrentLog(terminalOutput.getText());

        // Print the next prompt ready for the user
        terminalOutput.appendText(buildPrompt());
    }

    /**
     * Builds the terminal prompt based on the current user and directory in the GameState
     * @return A string representing the terminal prompt (e.g., "guest@rpg-os:home$ ")
     */
    private String buildPrompt() {
        return state.getCurrentUser() + "@rpg-os:" + state.getCurrentDirectory().getName() + "$ ";
    }
}