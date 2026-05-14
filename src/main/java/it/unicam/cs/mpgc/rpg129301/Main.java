package it.unicam.cs.mpgc.rpg129301;

import it.unicam.cs.mpgc.rpg129301.model.command.CommandParser;
import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.StartupEngine;
import it.unicam.cs.mpgc.rpg129301.view.AppLauncher;
import javafx.application.Application;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {

        Application.launch(AppLauncher.class, args);

        Scanner scanner = new Scanner(System.in);
        //System.out.println("=== RPG-OS TERMINAL v2.0 ===");
        //System.out.println("Type 'exit' to terminate the session.\n");
    }
}