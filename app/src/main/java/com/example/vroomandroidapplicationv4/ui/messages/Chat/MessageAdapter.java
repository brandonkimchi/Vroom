package com.example.vroomandroidapplicationv4.ui.messages.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {  //connects message data(List<Message>) to the chat UI(RecyclerView)

    private final List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) { // inflate Student message layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_user, parent, false);
        } else { // inflate AI message layout
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_ai, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) { //bind the actual message content to the view
        Message message = messageList.get(position);

        if (message.isImage()) { //hide the text view and show the image(which is the QR code)
            holder.messageText.setVisibility(View.GONE);
            holder.messageImage.setVisibility(View.VISIBLE);
            holder.messageImage.setImageResource(message.getImageResId());
        } else { // Show text message and hide image
            holder.messageImage.setVisibility(View.GONE);
            holder.messageText.setVisibility(View.VISIBLE);
            holder.messageText.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) { //help the onCreateViewHolder() decide which layout to inflate
        return messageList.get(position).isSent() ? 0 : 1; // Student = 0, AI = 1
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder { //holds the view(textview and imageview) inside eachh message item
        TextView messageText;
        ImageView messageImage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageImage = itemView.findViewById(R.id.messageImage);
        }
    }
}
