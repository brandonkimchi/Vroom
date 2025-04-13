package com.example.vroomandroidapplicationv4;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.vroomandroidapplicationv4.ui.messages.Chat.ChatFragment;

import com.example.vroomandroidapplicationv4.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.ImageButton;
import com.example.vroomandroidapplicationv4.ui.menu.BottomSheetMenu;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set Toolbar as the ActionBar
        setSupportActionBar(binding.toolbar);

        // Get the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_home);
        NavController navController = navHostFragment.getNavController();

        // Set up AppBarConfiguration
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_bar_home, R.id.navigation_bar_search,
                R.id.navigation_bar_calender, R.id.messagesFragment,
                R.id.navigation_bar_quiz)
                .build();

        // Set up ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Bottom Navigation setup
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        // Hide the Title for All Fragments
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ✅ Retrieve and log incoming user data
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        ArrayList<HashMap<String, Object>> bookings =
                (ArrayList<HashMap<String, Object>>) getIntent().getSerializableExtra("bookings");

        Log.d("USER_DATA", "Name: " + name);
        Log.d("USER_DATA", "Address: " + address);
        Log.d("USER_DATA", "Bookings: " + bookings);

        //Hamburger button top left
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetMenu bottomSheet = new BottomSheetMenu();
                bottomSheet.show(getSupportFragmentManager(), "BottomSheetMenu");
            }
        });
        // Listen for fragment changes to show/hide the menu button
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_bar_home) {
                btnMenu.setVisibility(View.VISIBLE); // Show in HomeFragment
            } else {
                btnMenu.setVisibility(View.GONE); // Hide in other screens
            }
        });

        if (getIntent().getBooleanExtra("openChatFragment", false)) {
            openChatFragment();
        }

    }

    private void openChatFragment() {
        ChatFragment chatFragment = new ChatFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_home, chatFragment) // Make sure this matches your layout
                .addToBackStack(null)
                .commit();
    }

}
