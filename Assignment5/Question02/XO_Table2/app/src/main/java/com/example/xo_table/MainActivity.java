package com.example.xo_table;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button[][] board;
    TextView textViewX, textViewO, textViewResult;

    String currentChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("HardwareIds") String uuid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(getBaseContext(), "uuid: " + uuid, Toast.LENGTH_LONG).show();

        currentChar = getIntent().getStringExtra("shape");

        textViewX = findViewById(R.id.tv_x);
        textViewO = findViewById(R.id.tv_o);
        textViewResult = findViewById(R.id.tv_result);

        if (currentChar.equals("o")) {
            textViewO.setBackgroundColor(Color.rgb(216, 193, 180));
            textViewX.setBackgroundColor(Color.argb(0, 0, 0, 0));
        } else {
            textViewX.setBackgroundColor(Color.rgb(216, 193, 180));
            textViewO.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }


        board = new Button[3][3];
        board[0][0] = findViewById(R.id.btn_00);
        board[0][1] = findViewById(R.id.btn_01);
        board[0][2] = findViewById(R.id.btn_02);
        board[1][0] = findViewById(R.id.btn_10);
        board[1][1] = findViewById(R.id.btn_11);
        board[1][2] = findViewById(R.id.btn_12);
        board[2][0] = findViewById(R.id.btn_20);
        board[2][1] = findViewById(R.id.btn_21);
        board[2][2] = findViewById(R.id.btn_22);

        textViewResult.setVisibility(View.GONE);

        if (getIntent().getStringExtra("mode").equals("Human")) {


            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = board[i][j];
                    button.setOnClickListener(v -> {
                        if (button.getText().toString().equals("")) {
                            button.setText(currentChar);
                            button.setTextColor(Color.BLACK);
                            String result = findWinner(board, currentChar);
                            if (!result.equals("no winner yet")) {
                                textViewResult.setText(result);
                                textViewResult.setVisibility(View.VISIBLE);
                            }
                            if (currentChar.equals("x")) {
                                currentChar = "o";
                                textViewO.setBackgroundColor(Color.rgb(216, 193, 180));
                                textViewX.setBackgroundColor(Color.argb(0, 0, 0, 0));
                            } else {
                                currentChar = "x";
                                textViewX.setBackgroundColor(Color.rgb(216, 193, 180));
                                textViewO.setBackgroundColor(Color.argb(0, 0, 0, 0));
                            }
                        }
                    });
                }
            }
            textViewResult.setOnClickListener(v -> {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        board[i][j].setText("");
                    }
                }
                textViewResult.setText("");
                currentChar = getIntent().getStringExtra("shape");
                assert currentChar != null;
                if (currentChar.equals("x")) {
                    textViewX.setBackgroundColor(Color.argb(200, 216, 193, 180));
                    textViewO.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else {
                    textViewO.setBackgroundColor(Color.argb(200, 216, 193, 180));
                    textViewX.setBackgroundColor(Color.argb(0, 0, 0, 0));
                }
                textViewResult.setBackgroundColor(Color.argb(200, 216, 193, 180));
                textViewResult.setVisibility(View.GONE);
            });
        } else {
            //NEXT PART
        }
    }

    String findWinner(Button[][] board, String current_char) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().toString().equals(current_char)
                    && board[i][1].getText().toString().equals(current_char)
                    && board[i][2].getText().toString().equals(current_char))
                return current_char + " won!";


            if (board[0][i].getText().toString().equals(current_char)
                    && board[1][i].getText().toString().equals(current_char)
                    && board[2][i].getText().toString().equals(current_char))
                return current_char + " won!";
        }

        if (board[0][0].getText().toString().equals(current_char)
                && board[2][2].getText().toString().equals(current_char)
                && board[1][1].getText().toString().equals(current_char))
            return current_char + " won!";
        if (board[0][2].getText().toString().equals(current_char)
                && board[2][0].getText().toString().equals(current_char)
                && board[1][1].getText().toString().equals(current_char))
            return current_char + " won!";


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().toString().equals("")) return "no winner yet";
            }
        }

        return "Draw!";
    }
}
