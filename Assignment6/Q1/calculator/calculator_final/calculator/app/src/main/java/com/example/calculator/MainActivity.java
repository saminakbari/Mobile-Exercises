package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    int openParsN = 0;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);

        button = findViewById(R.id.btn_c);
        button.setOnClickListener(v -> {
            textView.setText("0");
            openParsN = 0;
        });

        button = findViewById(R.id.btn_par);
        button.setOnClickListener(v -> {
            string = textView.getText().toString();
            if (string.substring(string.length() - 1)
                    .matches("(\\d)|(\\))")
                    && openParsN > 0) {
                string += ")";
                openParsN--;
            } else {
                if (string.equals("0")) string = "";
                else if (openParsN == 0 && !string.substring(string.length() - 1)
                        .matches("x|(\\+)|-|÷")) string += "x";
                string += "(";
                openParsN++;
            }
            textView.setText(string);
        });

        button = findViewById(R.id.btn_mod);
        button.setOnClickListener(v -> {
            string = textView.getText().toString();
            string = textView.getText().toString() + "%";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_div);
        button.setOnClickListener(v -> {

            string = textView.getText().toString() + "÷";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_x);
        button.setOnClickListener(v -> {
            string = textView.getText().toString() + "x";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_sub);
        button.setOnClickListener(v -> {
            string = textView.getText().toString() + "-";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_add);
        button.setOnClickListener(v -> {
            string = textView.getText().toString() + "+";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_point);
        button.setOnClickListener(v -> {
            string = textView.getText().toString() + ".";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_0);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "0";
            else string = currentString + "0";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_1);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "1";
            else string = currentString + "1";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_2);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "2";
            else string = currentString + "2";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_3);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "3";
            else string = currentString + "3";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_4);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "4";
            else string = currentString + "4";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_5);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "5";
            else string = currentString + "5";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_6);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "6";
            else string = currentString + "6";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_7);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "7";
            else string = currentString + "7";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_8);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "8";
            else string = currentString + "8";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_9);
        button.setOnClickListener(v -> {
            String currentString = textView.getText().toString();
            if (currentString.equals("0")) string = "9";
            else string = currentString + "9";
            textView.setText(string);
        });

        button = findViewById(R.id.btn_backspace);
        button.setOnClickListener(v -> {
            string = textView.getText().toString();
            if (string.length() == 1) string = "0";
            else string = string.substring(0, string.length() - 1);
            textView.setText(string);
        });

        button = findViewById(R.id.btn_equal);
        button.setOnClickListener(v -> {
            String text = textView.getText().toString();
            text = text.replace("x", "*");
            text = text.replace("÷", "/");

            try {
                Expression expression = new ExpressionBuilder(text).build();
                double result = expression.evaluate();

                if (result == Math.floor(result)) {
                    long flr = (long) result;
                    textView.setText(String.valueOf(flr));
                } else textView.setText(String.valueOf(result));
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "The expression format is invalid!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}