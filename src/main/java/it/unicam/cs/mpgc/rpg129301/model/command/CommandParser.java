package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private final Map<String, GameCommand> commands = new HashMap<>();

    public void register(String name, GameCommand command) {
        commands.put(name, command);
    }

    public Map<String, GameCommand> getRegisteredCommands() {
        return this.commands;
    }

    public String process(String input, GameState state) {

        // If there is no input, return an empty string (no command to process)
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // Divide the input in arguments: "ls -a" becomes ["ls", "-a"]
        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();

        // From commands list, get the correct command from the name
        GameCommand cmd = commands.get(commandName);

        // If the command exists, execute it with the arguments (everything after the command name) and return the result
        if (cmd != null) {
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            return cmd.execute(args, state);
        } else {
            return "Command not found: '" + commandName + "'.";
        }
    }
}