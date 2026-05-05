package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    private final Map<String, GameCommand> commands = new HashMap<>();

    public void register(String name, GameCommand command) {
        commands.put(name.toLowerCase(), command);
    }

    public String process(String input, GameState state) {

        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // "ls -a" diventa ["ls", "-a"]
        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();

        GameCommand cmd = commands.get(commandName);

        if (cmd != null) {
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);
            return cmd.execute(args, state);
        } else {
            return "Comando non trovato: '" + commandName + "'.";
        }
    }
}