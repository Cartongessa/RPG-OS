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
     * Initializes the terminal by setting up the game state and command parser, and displays the initial prompt.
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
     * Handles the command entered by the user, processes it through the CommandParser, and updates the terminal output.
     */
    @FXML
    public void handleCommand() {
        // Get the input
        String input = commandInput.getText();

        // If the input is "exit", close the app
        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        // Append input to the terminal
        terminalOutput.appendText(input + "\n");

        // Save the log
        state.setCurrentLog(terminalOutput.getText());

        // Process the command and get the response
        String response = parser.process(input, state);

        if (response != null && !response.isEmpty()) {
            terminalOutput.appendText(response + "\n");
        }

        terminalOutput.appendText(buildPrompt());

        commandInput.clear();

        if (state.getObjective() != null && state.getObjective().isCompleted(state)) {
            terminalOutput.appendText("\n[SYSTEM]: Objective completed! " + state.getObjective().getSuccessMessage() + "\n");
        }

        // Feature for debugging
        if (input.equalsIgnoreCase("objective done")) {
            terminalOutput.appendText("\n[SYSTEM]: Objective completed! " + state.getObjective().getSuccessMessage() + "\n");
        }
    }

    /**
     * Builds the terminal prompt based on the current user and directory in the GameState
     * @return A string representing the terminal prompt (e.g., "guest@rpg-os:home$ ")
     */
    private String buildPrompt() {
        return state.getCurrentUser() + "@rpg-os:" + state.getCurrentDirectory().getName() + "$ ";
    }
}