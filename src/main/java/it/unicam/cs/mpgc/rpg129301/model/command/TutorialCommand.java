package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;

public class TutorialCommand implements GameCommand {

    private static final String TUTORIAL_TEXT = """
            - This is a Unix terminal.
            - Use the "hint" command if you need help.
            - use the "help" command for a list of available commands.
            - use the "help <command>" for a better description of a specific command.
            - in each level you will have to gain access to something or execute something, usually specified in the readme file.
            - in some levels you will find .man files that you can use "learn" on to improve your stats.. don't worry, it's not skill issue.
            - use the "tutorial" command to review this guide
            """;

    @Override
    public String execute(String[] args, GameState state) {
        return TUTORIAL_TEXT;
    }

    @Override
    public String getCommandName() {
        return "tutorial";
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    // Se la tua interfaccia richiede un metodo getDescription() o simile per l'help:
    public String getDescription() {
        return "Shows a brief tutorial of how to use the OS.";
    }

    @Override
    public String getBriefDescription() {
        return "Shows a brief tutorial of how to use the OS.";
    }
}