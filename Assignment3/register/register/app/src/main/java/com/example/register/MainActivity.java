package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> users;
    EditText usernameET;
    EditText passwordET;
    EditText confirmationET;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        usernameET = (EditText) findViewById(R.id.et_uname);
        passwordET = (EditText) findViewById(R.id.et_password);
        confirmationET = (EditText) findViewById(R.id.et_confirmation);
        button = (Button) findViewById(R.id.btn_signUp);

        button.setOnClickListener(v -> {
            String message = makeNewUser(usernameET.getText().toString(),
                    passwordET.getText().toString(), confirmationET.getText().toString());
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        });
    }

    String makeNewUser(String username, String password, String confirmation) {
        if (username.equals("")) return "Enter your username!";
        if (password.equals("")) return "Enter your password!";
        if (confirmation.equals("")) return "Confirm your password!";
        if (!password.equals(confirmation)) return "Password and Confirmation do not match!";
        for (User user : users) {
            if (user.username.equals(username)) return "Username already exists!";
        }
        users.add(new User(username, password));
        return "You signed up successfully!";
    }
}