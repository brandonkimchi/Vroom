package com.example.vroomandroidapplicationv4.ui.messages;

public class ChatItem {
    private String name;
    private String lastMessage;
    private String time;
    private int profileImage;

    public ChatItem(String name, String lastMessage, String time, int profileImage) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
