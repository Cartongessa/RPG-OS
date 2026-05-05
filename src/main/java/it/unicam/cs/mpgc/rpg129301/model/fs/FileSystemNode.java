package it.unicam.cs.mpgc.rpg129301.model.fs;

public abstract class FileSystemNode {
    protected String name;

    public FileSystemNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
