package it.unicam.cs.mpgc.rpg129301.model.command;

import it.unicam.cs.mpgc.rpg129301.model.GameState;
import it.unicam.cs.mpgc.rpg129301.model.fs.FileSystemNode;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameDirectory;
import it.unicam.cs.mpgc.rpg129301.model.fs.GameFile;

public class LsCommand implements GameCommand {
    @Override
    public String execute(String[] args, GameState state) {

        // Controlla se inserito parametro per mostrare i file nascosti
        boolean showHidden = args.length > 0 && args[0].equals("-a");
        StringBuilder output = new StringBuilder();

        for (FileSystemNode child : state.getCurrentDirectory().getChildren().values()) {

            boolean isHidden = (child instanceof GameFile) && ((GameFile) child).isHidden();

            if (showHidden || !isHidden) {
                // Per dare l'effetto visivo di Linux, aggiunto un punto al nome dei file nascosti
                String displayName = isHidden ? "." + child.getName() : child.getName();

                // Formattazione per distinguere file e cartelle
                String typePrefix = (child instanceof GameDirectory) ? "[DIR]  " : "[FILE] ";
                output.append(typePrefix).append(displayName).append("\n");
            }
        }

        return output.toString().trim();
    }

    @Override
    public String getDescription() {
        return "ls [-a] : Mostra il contenuto della directory corrente.";
    }
}