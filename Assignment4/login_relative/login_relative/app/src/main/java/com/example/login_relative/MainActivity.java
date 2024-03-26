package com.example.login_relative;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_relative.User;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<User> users;
    Button button;
    EditText unameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = new ArrayList<>(Arrays.asList(new User("samin", "1234"),
                new User("kimia", "456"), new User("moeein", "1162"),
                new User("melika", "1111"), new User("torobche", "555")));
        button = findViewById(R.id.btn_login);
        unameET = findViewById(R.id.et_username);
        passwordET = findViewById(R.id.et_password);

        button.setOnClickListener(v -> {
            String message = checkLogin(unameET.getText().toString(),
                    passwordET.getText().toString());
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        });
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
