package it.unicam.cs.mpgc.rpg129301.model.fs;

public abstract class FileSystemNode {
    private String name;
    protected transient GameDirectory parent;

    public FileSystemNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(GameDirectory parent) {
        this.parent = parent;
    }

    public GameDirectory getParent() {
        return parent;
    }
}
