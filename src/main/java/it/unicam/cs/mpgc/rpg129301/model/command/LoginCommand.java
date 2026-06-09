package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import java.util.Map;

public class LoginCommand implements GameCommand {

    @Override
    public String execute(String[] args, GameState state) {
        // If there is not any argument, return an error message
        if (args.length < 1) {
            return "Usage: login <username> [password]";
        }

        // Set the target username and the provided password (if any)
        String targetUsername = args[0];
        String providedPassword = args.length > 1 ? args[1] : "";

        // Map all the accounts for this level
        Map<String, String> accounts = state.getAccounts();

        // If the account does not exist, return an error message
        if (!accounts.containsKey(targetUsername)) {
            return "User '" + targetUsername + "' does not exist.";
        }

        // Check the password for the account. If the password is incorrect, return an error message
        String correctPassword = accounts.get(targetUsername);
        if (correctPassword != null && !correctPassword.isEmpty()) {
            if (!correctPassword.equals(providedPassword)) {
                return "Authentication failure: incorrect password.";
            }
        }

        // Set the target user as the current user
        state.setCurrentUser(targetUsername);
        return "Login successful. Welcome, " + targetUsername + ".";
    }

    @Override
    public String getCommandName() { return "login"; }

    @Override
    public String getCommandUsage() { return " <username> <password>"; }

    @Override
    public String getDescription() { return "Logs in as a different user."; }

    @Override
    public String getBriefDescription() { return "Switch user account."; }
}