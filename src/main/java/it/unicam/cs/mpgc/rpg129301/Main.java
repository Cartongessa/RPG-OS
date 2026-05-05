package it.unicam.cs.mpgc.rpg129301;

import it.unicam.cs.mpgc.rpg129301.model.command.*;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

import java.util.Scanner;

public class Main {
    static void main() {

        // --- Creazione del file system) ---
        GameDirectory root = new GameDirectory("/", null);
        GameDirectory homeDir = new GameDirectory("home", root);
        GameDirectory guestDir = new GameDirectory("guest", homeDir);
        GameDirectory binDir = new GameDirectory("bin", root);

        root.addChild(homeDir);
        root.addChild(binDir);
        homeDir.addChild(guestDir);

        // --- Aggiunta file livello 1 ---
        guestDir.addChild(new GameFile("readme.txt", "Storia inutile del gioco...", false));
        guestDir.addChild(new GameFile("passwords_backup", "user_target_pwd_123", true)); // Nascosto!

        // --- Inizializzazione stato e parser ---
        GameState state = new GameState(guestDir); // Partiamo da /home/guest

        CommandParser parser = new CommandParser();
        parser.register("ls", new LsCommand());
        parser.register("cd", new CdCommand());

        // --- Game loop ---
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== THE GUEST ACCOUNT ===");
        System.out.println("Connessione stabilita. Digita 'exit' per uscire.\n");

        while (true) {
            // prompt visivo: nomeutente@rpg:cartella_attuale$
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