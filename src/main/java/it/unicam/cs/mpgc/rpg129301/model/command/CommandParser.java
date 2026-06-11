package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    private final Map<String, GameCommand> commands;

    public CommandParser() {
        this.commands = new HashMap<>();
    }

    public void register(String name, GameCommand command) {
        this.commands.put(name.toLowerCase(), command);
    }

    public Map<String, GameCommand> getRegisteredCommands() {
        return this.commands;
    }

    /**
     * Processes the raw input string from the terminal.
     */
    public String process(String input, GameState state) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // Split the input into the command and its arguments
        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();

        GameCommand command = this.commands.get(commandName);

        if (command != null) {
            // Command exists, execute it
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            return command.execute(args, state);
        } else {
            // The system notices suspicious activity
            int penalty = 10; // Each unrecognized command increases trace level by 10%
            state.incrementTraceLevel(penalty);

            // Check if this mistake caused a Game Over
            if (state.isGameOverByTrace()) {
                return "[CRITICAL WARNING] Command not found: '" + commandName + "'.\n" +
                        "Trace Level reached 100%. You have been detected and disconnected.";
            }

            return "[WARNING] Command not found: '" + commandName + "'.\n" +
                    "Suspicious activity logged. Trace Level increased to " + state.getTraceLevel() + "%.";
        }
    }
}