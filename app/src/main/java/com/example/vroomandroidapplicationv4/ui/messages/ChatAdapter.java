package com.example.vroomandroidapplicationv4.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatItem> chatList;

    public ChatAdapter(List<ChatItem> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatItem chat = chatList.get(position);
        holder.name.setText(chat.getName());
        holder.lastMessage.setText(chat.getLastMessage());
        holder.time.setText(chat.getTime());
        holder.profileImage.setImageResource(chat.getProfileImage());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("chatName", chat.getName());
            bundle.putInt("profileImage", chat.getProfileImage());

            Navigation.findNavController(v).navigate(R.id.chatFragment, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView name, lastMessage, time;
        ImageView profileImage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chatName);
            lastMessage = itemView.findViewById(R.id.chatMessage);
            time = itemView.findViewById(R.id.chatTime);
            profileImage = itemView.findViewById(R.id.chatImage);
        }
    }
}
