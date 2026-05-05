package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;

public class CdCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {
        if (args.length == 0) {
            return "Errore: specificare il nome di una cartella (o '..').";
        }

        String targetName = args[0];
        GameDirectory currentDir = state.getCurrentDirectory();

        // Gestione cd ..
        if (targetName.equals("..")) {
            if (currentDir.getParent() != null) {
                state.setCurrentDirectory(currentDir.getParent());
                return "";
            } else {
                return "Errore: sei già nella directory radice (/).";
            }
        }

        FileSystemNode targetNode = currentDir.getChild(targetName);

        if (targetNode instanceof GameDirectory) {
            state.setCurrentDirectory((GameDirectory) targetNode);
            return "";
        } else if (targetNode != null) {
            return "Errore: '" + targetName + "' e' un file, non una cartella.";
        } else {
            return "Errore: nessuna directory trovata con il nome '" + targetName + "'.";
        }
    }

    @Override
    public String getCommandName() {
        return "cd";
    }

    @Override
    public String getDescription() {
        return "cd [dir] : Cambia la directory corrente navigando nell'albero.";
    }
}
