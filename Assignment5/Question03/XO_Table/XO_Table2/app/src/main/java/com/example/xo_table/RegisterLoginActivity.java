package com.example.xo_table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterLoginActivity extends AppCompatActivity {
    ArrayList<User> users;
    EditText usernameET;
    EditText passwordET;
    EditText confirmationET;
    Button button;
    Button button2;
    EditText unameET;
    EditText passwordET2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        users = new ArrayList<>();
        usernameET = findViewById(R.id.et_uname);
        passwordET = findViewById(R.id.et_password);
        confirmationET = findViewById(R.id.et_confirmation);
        button = findViewById(R.id.btn_signUp);

        button.setOnClickListener(v -> {
            String message = makeNewUser(usernameET.getText().toString(),
                    passwordET.getText().toString(), confirmationET.getText().toString());
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        });

        button2 = findViewById(R.id.btn_login);
        unameET = findViewById(R.id.et_username2);
        passwordET2 = findViewById(R.id.et_password2);

        button2.setOnClickListener(v -> {
            String message = checkLogin(unameET.getText().toString(),
                    passwordET.getText().toString());
            if (message.equals("You logged in successfully!")) {
                Intent intent = new Intent(RegisterLoginActivity.this,
                        ChoiceActivity.class);
                this.finish();
                startActivity(intent);
            }
            else Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
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

    public void onClick(View view) {
        String message = getPassword(unameET.getText().toString());
        if (unameET.getText().toString().equals("")) message = "Enter your username!";
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    String checkLogin(String username, String password) {
        if (username.equals("")) return "Enter your username!";
        if (password.equals("")) return "Enter your password!";
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password))
                    return "You logged in successfully!";
                return "Password is not correct!";
            }
        }
        return "Username does not exist!";
    }

    String getPassword(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return "Your password is: " + user.getPassword();
        }
        return "Username does not exist!";
    }
}
