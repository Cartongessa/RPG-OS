package it.unicam.cs.mpgc.rpg129301;

import it.unicam.cs.mpgc.rpg129301.model.command.CommandParser;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.view.AppLauncher;
import javafx.application.Application;

import java.util.Scanner;

public class Main {
     static void main(String[] args) {

        StartupEngine engine = new StartupEngine();

        // Simply pass '1' to start Level 1
        GameState state = engine.setupGame(1);
        CommandParser parser = engine.setupParser();

        Application.launch(AppLauncher.class, args);


        Scanner scanner = new Scanner(System.in);
        System.out.println("=== RPG-OS TERMINAL v2.0 ===");
        System.out.println("Type 'exit' to terminate the session.\n");

        // Main Game Loop
        while (true) {
            // Dynamic prompt showing the current directory name
            System.out.print("user@rpg-os:" + state.getCurrentDirectory().getName() + "$ ");

            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;

            // Process the input through the router
            String response = parser.process(input, state);

            // Print the response if it's not empty
            if (!response.isEmpty()) {
                System.out.println(response);
            }
        }

        System.out.println("Closing connection...");
        scanner.close();
    }
}