package com.example.vroomandroidapplicationv4.ui.messages.Chat;

public class Message {
    private final String text;
    private final boolean isUser; // True for user messages, False for AI messages
    private boolean isImage;
    private int imageResId;

    // Text message constructor
    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
        this.isImage = false;
    }

    // Image message constructor
    public Message(int imageResId, boolean isUser, boolean isImage) {
        this.text = null; // ensure text is initialized
        this.imageResId = imageResId;
        this.isUser = isUser;
        this.isImage = isImage;
    }

    public String getText() {
        return text;
    }

    public boolean isSent() {
        return isUser; // isSent() is now consistent with user messages
    }

    public boolean isImage() {
        return isImage;
    }
    public int getImageResId() {
        return imageResId;
    }
}
