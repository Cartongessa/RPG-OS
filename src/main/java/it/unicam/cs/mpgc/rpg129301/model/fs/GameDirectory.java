package it.unicam.cs.mpgc.rpg129301.model.fs;

import java.util.HashMap;
import java.util.Map;

public class GameDirectory extends FileSystemNode {
    private Map<String, FileSystemNode> children;

    public GameDirectory(String name) {
        super(name);
        this.children = new HashMap<>();
    }

    public void addChild(FileSystemNode node) {
        children.put(node.getName(), node);
    }

    public FileSystemNode getChild(String childName) {
        return children.get(childName);
    }

    public Map<String, FileSystemNode> getChildren() {
        return children;
    }
}
