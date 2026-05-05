package it.unicam.cs.mpgc.rpg129301;

import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.model.command.*;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StartupEngine engine = new StartupEngine();

        GameDirectory startDir = engine.fileSystemSetup();
        GameState state = new GameState(startDir);

        CommandParser parser = engine.parserSetup();

        // --- Game loop ---
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== THE GUEST ACCOUNT ===");
        System.out.println("Connessione stabilita. Digita 'exit' per uscire.\n");

        while (true) {
            // prompt: nomeutente@rpg:cartella_attuale$
            System.out.print("guest@rpg:" + state.getCurrentDirectory().getName() + "$ ");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("Disconnessione dal sistema in corso...");
                break;
            }

            String output = parser.process(input, state);

            if (!output.isEmpty()) {
                System.out.println(output);
            }
        }

        scanner.close();
    }
}