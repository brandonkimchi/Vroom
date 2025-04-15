package com.example.vroomandroidapplicationv4.ui.messages.Chat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.utils.MessageObserver;
import com.example.vroomandroidapplicationv4.utils.NotificationManagerSingleton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private String userName;
    private int userImage;
    private static final int PERMISSION_REQUEST_CODE = 1001; // any specific integer used to get permission for notification

    private static final String OPENAI_API_KEY = "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"; //OpenAI API key

    private EditText messageInput;
    private TextView chatUserName;
    private ImageView chatUserImage;
    private RecyclerView recyclerViewChat;
    private ImageButton chatBackButton;
    private ImageButton sendButton;

    private static final Map<String, List<Message>> chatHistoryMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false); // load XML layout to the chat
        //initialize UI stuff
        messageInput = view.findViewById(R.id.messageInput);
        chatUserName = view.findViewById(R.id.chatUserName);
        chatUserImage = view.findViewById(R.id.chatUserImage);
        recyclerViewChat = view.findViewById(R.id.recyclerViewChat);
        chatBackButton = view.findViewById(R.id.chatBackButton);
        sendButton = view.findViewById(R.id.sendButton);

        //If theres not null then itll get the details from another activity
        if (getArguments() != null) {
            userName = getArguments().getString("chatName", "Unknown");
            userImage = getArguments().getInt("profileImage", R.drawable.blank_profile);
        }
        //Ask user for notification permission
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        }
        //UI setting the username and image with the recyclerview
        chatUserName.setText(userName);
        chatUserImage.setImageResource(userImage);

        if (chatHistoryMap.containsKey(userName)) {
            messageList = chatHistoryMap.get(userName);
        } else {
            messageList = new ArrayList<>();
            chatHistoryMap.put(userName, messageList);
        }

        messageAdapter = new MessageAdapter(messageList);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChat.setAdapter(messageAdapter);

        //the 2 buttons, back and send button
        chatBackButton.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        sendButton.setOnClickListener(v -> sendMessage());

        //adding the notification observer for the message updates
        NotificationManagerSingleton notificationManager = NotificationManagerSingleton.getInstance(requireContext());
        addObserver(notificationManager);

        return view;
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim(); //the messaged typed by the user, .trim to remove trailing spaces

        if (!messageText.isEmpty()) { //if not empty
            messageList.add(new Message(messageText, true)); //add the message to the list if isUser is true
            messageAdapter.notifyItemInserted(messageList.size() - 1); //update the UI(messageadapter)
            recyclerViewChat.scrollToPosition(messageList.size() - 1);
            messageInput.setText(""); // Clears input box after sending a message

            new Thread(() -> { // make it run in the background so AI does free
                String aiResponse = getAIResponse(messageText); //calls getAIResponse function
                if (aiResponse != null) { //if not null
                    getActivity().runOnUiThread(() -> {
                        messageList.add(new Message(aiResponse, false)); //add ai message to list if AI
                        messageAdapter.notifyItemInserted(messageList.size() - 1); //update UI
                        recyclerViewChat.scrollToPosition(messageList.size() - 1);

                        if (aiResponse.toLowerCase().contains("[qr code]") || aiResponse.toLowerCase().contains("[provide qr code]") || aiResponse.toLowerCase().contains("here is my qr code")) {
                            messageList.add(new Message(R.drawable.qrcode, false, true));
                            messageAdapter.notifyItemInserted(messageList.size() - 1);
                            recyclerViewChat.scrollToPosition(messageList.size() - 1);
                        }

                        notifyObservers(aiResponse);
                    });
                }
            }).start();
        }
    }

    private String getAIResponse(String userMessage) { // takes user typed message as a string
        try {
            OkHttpClient client = new OkHttpClient(); // creates a new OkHttpClient which is a library used to make HTTP requests
            MediaType mediaType = MediaType.parse("application/json"); //set it to json

            //JSON string to send to API
            String json = "{ " +
                    "\"model\": \"gpt-3.5-turbo\", " +
                    "\"messages\": [" +
                    "{\"role\": \"system\"," +
                    "\"content\": \"You are a professional driving instructor. Provide detailed driving tips and guidance. If the user asks for your payment or QR code, respond with: 'Sure, here is my QR code for payment:' without saying insert QR code\"}," +
                    "{\"role\": \"user\"," +
                    " \"content\": \"" + userMessage + "\"}" + "]}";

            RequestBody body = RequestBody.create(mediaType, json); //wraps the JSON string in a way it can be sent in the HTTP request body
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute(); //sends request
            String responseBody = response.body().string(); //wait for the response

            if (response.isSuccessful()) {
                try {
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                    return jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject()
                            .get("message").getAsJsonObject().get("content").getAsString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error parsing AI response.";
                }
            } else {
                return "API Request Failed: " + response.code() + " - " + responseBody;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Sorry, I couldn't process your request.";
    }

    private List<MessageObserver> observers = new ArrayList<>();
    public void addObserver(MessageObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MessageObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (MessageObserver observer : observers) {
            observer.onNewMessage(message);
        }
    }
}

