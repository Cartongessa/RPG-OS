package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Map;

/**
 * Command to provide information about available game commands.
 */
public class HelpCommand implements GameCommand {
    private final Map<String, GameCommand> commands;

    public HelpCommand(Map<String, GameCommand> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args, GameState state) {
        // Case 1: specific research (e.g., "help cd")
        if (args.length > 0) {
            String target = args[0].toLowerCase();
            GameCommand cmd = commands.get(target);
            if (cmd != null) {
                return "Command: " + target + cmd.getCommandUsage() + "\nDescription: " + cmd.getDescription();
            } else {
                return "Unknown command: '" + target + "'. Type 'help' for a list of commands.";
            }
        }

        // Case 2: list all
        StringBuilder sb = new StringBuilder();
        sb.append("Available commands:\n");
        for (Map.Entry<String, GameCommand> entry : commands.entrySet()) {
            sb.append(entry.getValue().getCommandName()).append(entry.getValue().getCommandUsage()).append(": ").append(entry.getValue().getBriefDescription()).append("\n");
        }
        sb.append("\nType 'help [command]' for more details.");
        return sb.toString();
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getCommandUsage() {
        return " [command]";
    }

    @Override
    public String getDescription() {
        return "Displays help information for commands.";
    }

    @Override
    public String getBriefDescription() {
        return "Displays help information for commands.";
    }
}