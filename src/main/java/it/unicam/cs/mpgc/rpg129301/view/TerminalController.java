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

    @FXML
    public void initialize() {
        StartupEngine engine = new StartupEngine();

        GameState state = engine.setupGame(1);
        CommandParser parser = engine.setupParser();

        // print the title
        terminalOutput.appendText("=== RPG-OS TERMINAL v2.0 ===\n");
        terminalOutput.appendText("Type 'exit' to terminate the session.\n\n");

        this.state = state;
        this.parser = parser;
    }

    @FXML
    public void handleCommand() {

        String input = commandInput.getText();

        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        String response = parser.process(input, state);

        terminalOutput.appendText("user@rpg-os:" + state.getCurrentDirectory().getName() + "$ " + input + "\n");
        terminalOutput.appendText(response + "\n");

        commandInput.clear();
    }
}