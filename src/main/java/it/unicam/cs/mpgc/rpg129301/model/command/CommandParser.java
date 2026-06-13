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

        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();

        GameCommand command = this.commands.get(commandName);

        if (command != null) {
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            return command.execute(args, state);
        } else {
            // Returns a special flag indicating that the command is unknown
            return "[UNKNOWN_COMMAND] " + commandName;
        }
    }
}