package it.unicam.cs.mpgc.rpg129301.model.fs;

public class GameFile extends FileSystemNode {
    private String content;
    private boolean isHidden;

    public GameFile(String name, String content, boolean isHidden) {
        super(name);
        this.content = content;
        this.isHidden = isHidden;
    }

    public String getContent() {
        return content;
    }

    public boolean isHidden() {
        return isHidden;
    }
}