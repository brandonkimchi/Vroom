package com.example.vroomandroidapplicationv4.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomandroidapplicationv4.R;
import com.example.vroomandroidapplicationv4.databinding.FragmentMessagesBinding;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    private FragmentMessagesBinding binding;
    private List<ChatItem> chatList;
    private ChatAdapter chatAdapter;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize chat list
        chatList = new ArrayList<>();
        chatList.add(new ChatItem("Zachary Lee", "Can I ask if you are able to have your lessons at a cheaper rate if I do all 16...", "9:45 AM", R.drawable.profile_zachary_lee));
        chatList.add(new ChatItem("Vernice Kah", "Can I ask if you are able to have your lessons at a cheaper rate if I do all 16...", "11:11 AM", R.drawable.profile_vernice_kah));

        // Setup RecyclerView
        RecyclerView recyclerView = binding.recyclerViewMessages;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(chatAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
