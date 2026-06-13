package it.unicam.cs.mpgc.rpg129301.model.fs;

public class GameFile extends FileSystemNode {
    private String content;
    private boolean isHidden;

    // Exploit requirements
    private String requiredSkill;
    private int requiredLevel;

    // Exploit outcomes
    private String targetUserOnSuccess;
    private String successMessage;

    /**
     * Basic constructor without required skill type and level
     */
    public GameFile(String name, String content, boolean isHidden) {
        super(name);
        this.content = content;
        this.isHidden = isHidden;
        this.requiredSkill = null;
        this.requiredLevel = 0;
    }

    /**
     * Advanced constructor including skill type and level
     */
    public GameFile(String name, String content, boolean isHidden, String requiredSkill, int requiredLevel, String targetUserOnSuccess, String successMessage) {
        super(name);
        this.content = content;
        this.isHidden = isHidden;
        this.requiredSkill = requiredSkill;
        this.requiredLevel = requiredLevel;
        this.targetUserOnSuccess = targetUserOnSuccess;
        this.successMessage = successMessage;
    }

    public String getContent() {
        return content;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public String getTargetUserOnSuccess() {
        return targetUserOnSuccess;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}