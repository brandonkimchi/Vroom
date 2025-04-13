package com.example.vroomandroidapplicationv4;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.vroomandroidapplicationv4.ui.messages.Chat.ChatFragment;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseDebug"; // Define custom tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Checking database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

//        myRef.setValue("Hello, World!");
        Log.d(TAG, "Database write operation executed."); // Log the write operation

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FirebaseDatabase", "Database Dump: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FirebaseDatabase", "Failed to read database.", databaseError.toException());
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "Network connected: " + isConnected);

    }

    // Creating an 'OnClick' Event Handler method/function for the Button 'Views'.
    // This Event Handler changes the 'Activity'/page from the current 'Activity'/page
    // to the 'SignInActivity' 'Activity'/page
    public void changeToSignInActivity(View v){
        // Android App development terminology:
        // Intent - the transfer of data from 1 activity/page of the app to another

        // - SettingsActivity - the name of the other 'Activty'/page to navigate to
        // - 'this' - refers to the current 'Activty'/page
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

}