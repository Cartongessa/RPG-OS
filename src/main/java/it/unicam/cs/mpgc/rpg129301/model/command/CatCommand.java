package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class CatCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {
        if (args.length == 0) {
            return "Errore: specificare il nome di una cartella (o '..').";
        }

        String targetName = args[0];

        GameDirectory currentDir = state.getCurrentDirectory();
        FileSystemNode targetNode = currentDir.getChild(targetName);

        if (targetNode == null) {
            return "Errore: nessun file trovato con il nome '" + targetName + "'.";
        } else if (targetNode instanceof GameDirectory) {
            return "Errore: '" + targetName + "' e' una cartella, non un file.";
        } else {
            return ((GameFile) targetNode).getContent();
        }
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public String getDescription() {
        return "cat [file] : Mostra il contenuto di un file.";
    }
}
