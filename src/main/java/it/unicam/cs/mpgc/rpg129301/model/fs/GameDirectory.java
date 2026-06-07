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

    /**
     * Navigates to parent to find the root directory
     */
    public GameDirectory getRoot() {
        GameDirectory node = this;
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    /**
     * Recursively search for a directory with the given name
     */
    public GameDirectory findDirectoryByName(String targetName) {
        if (this.getName().equals(targetName)) {
            return this;
        }

        for (FileSystemNode child : children.values()) {
            if (child instanceof GameDirectory dir) {
                GameDirectory found = dir.findDirectoryByName(targetName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null; // Not found in this branch
    }
}
