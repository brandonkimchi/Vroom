package com.example.vroomandroidapplicationv4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText passwordInput;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.editTextText2);         // Update with your actual ID
        passwordInput = findViewById(R.id.editTextText3); // Update with your actual ID

        databaseRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
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

    // Creating an 'OnClick' Event Handler method/function for the Button 'Views'.
    // This Event Handler changes the 'Activity'/page from the current 'Activity'/page
    // to the 'LogInActivity' 'Activity'/page
    public void changeToLogInActivity(View v){
        // Android App development terminology:
        // Intent - the transfer of data from 1 activity/page of the app to another

        // - SettingsActivity - the name of the other 'Activty'/page to navigate to
        // - 'this' - refers to the current 'Activty'/page
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    // Called when "Continue" button is clicked
    public void verifyUserLogin(View v) {
        String name = nameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both name and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase: Check if credentials match
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean matchFound = false;
                String dbName = "";
                String dbAddress = "";
                ArrayList<HashMap<String, Object>> bookings = new ArrayList<>();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String fetchedName = userSnapshot.child("name").getValue(String.class);
                    String fetchedPassword = userSnapshot.child("password").getValue(String.class);

                    if (name.equals(fetchedName) && password.equals(fetchedPassword)) {
                        matchFound = true;
                        dbName = fetchedName;
                        dbAddress = userSnapshot.child("address").getValue(String.class);

                        // Get bookings
                        DataSnapshot bookingsSnapshot = userSnapshot.child("bookings");
                        for (DataSnapshot booking : bookingsSnapshot.getChildren()) {
                            HashMap<String, Object> bookingData = (HashMap<String, Object>) booking.getValue();
                            bookings.add(bookingData);
                        }
                        break;
                    }
                }

                if (matchFound) {
                    Toast.makeText(LogInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Pass data via Intent to HomeActivity
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    intent.putExtra("name", dbName);
                    intent.putExtra("address", dbAddress);
                    intent.putExtra("bookings", bookings); // Serializable object
                    startActivity(intent);

                } else {
                    // Red error Toast
                    Toast toast = Toast.makeText(LogInActivity.this, "Incorrect Username or Password!", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    if (toastView != null) {
                        toastView.setBackgroundColor(Color.parseColor("#FFCDD2")); // Light red
                    }
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogInActivity.this, "Database error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}