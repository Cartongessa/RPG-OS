package it.unicam.cs.mpgc.rpg129301.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TerminalController {
    @FXML private TextArea terminalOutput;
    @FXML private TextField commandInput;

    @FXML
    public void handleCommand() {
        String input = commandInput.getText();
        terminalOutput.appendText("> " + input + "\n");
        commandInput.clear();
    }
}