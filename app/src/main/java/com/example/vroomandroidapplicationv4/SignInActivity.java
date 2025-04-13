package com.example.vroomandroidapplicationv4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private Spinner addressSpinner;
    private EditText nameInput;
    private EditText passwordInput;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.editTextText);
        passwordInput = findViewById(R.id.editTextText4);
        addressSpinner = findViewById(R.id.spinner);

        List<String> towns = new ArrayList<>();
        towns.add("Woodlands");
        towns.add("Yishun");
        towns.add("Ang Mo Kio");
        towns.add("Hougang");
        towns.add("Sengkang");
        towns.add("Serangoon");
        towns.add("Punggol");
        towns.add("Jurong West");
        towns.add("Bukit Batok");
        towns.add("Bukit Panjang");
        towns.add("Clementi");
        towns.add("Bukit Timah");
        towns.add("Toa Payoh");
        towns.add("Bishan");
        towns.add("Novena");
        towns.add("Queenstown");
        towns.add("Kallang");
        towns.add("Geylang");
        towns.add("Marine Parade");
        towns.add("Tampines");
        towns.add("Pasir Ris");
        towns.add("Bedok");
        towns.add("Changi");

        Collections.sort(towns);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, towns);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressSpinner.setAdapter(adapter);

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Registered_Users");
    }

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
    public void createAccount(View v){
        String name = nameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String address = addressSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int userId = (int) snapshot.getChildrenCount() + 1;
                User user = new User(userId, name, address, password, Collections.singletonList(new HashMap<>()));
                databaseRef.child(String.valueOf(userId)).setValue(user);
                Toast.makeText(SignInActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                Log.d("USER_INFO", user.toString());

                Intent i = new Intent(SignInActivity.this, LogInActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignInActivity.this, "Error accessing database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Creating an 'OnClick' Event Handler method/function for the Button 'Views'.
    // This Event Handler changes the 'Activity'/page from the current 'Activity'/page
    // to the 'HomeActivity' 'Activity'/page
    public void changeToHomeActivity(View v){
        // Android App development terminology:
        // Intent - the transfer of data from 1 activity/page of the app to another

        // - SettingsActivity - the name of the other 'Activty'/page to navigate to
        // - 'this' - refers to the current 'Activty'/page
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    // Creating an 'OnClick' Event Handler method/function for the Button 'Views'.
    // This Event Handler changes the 'Activity'/page from the current 'Activity'/page
    // to the 'HomeActivity' 'Activity'/page
    public void changeToLogInActivity(View v){
        // Android App development terminology:
        // Intent - the transfer of data from 1 activity/page of the app to another

        // - SettingsActivity - the name of the other 'Activty'/page to navigate to
        // - 'this' - refers to the current 'Activty'/page
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }
}