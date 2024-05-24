package com.example.xo_table;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button[][] board;
    TextView textViewX, textViewO, textViewResult, textViewProb;

    String currentChar;

    @SuppressLint("SetTextI18n")
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
        textViewProb = findViewById(R.id.tv_probability);

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
            if (Objects.equals(getIntent().getStringExtra("mode"), "CPU"))
                textViewProb.setText("No one has a higher chance to win!");
        });

        if (Objects.equals(getIntent().getStringExtra("mode"), "Human")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = board[i][j];
                    button.setOnClickListener(v -> {
                        if (button.getText().toString().equals("")) {
                            button.setText(currentChar);
                            button.setTextColor(Color.BLACK);
                            String result = findWinner(board);
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

        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = board[i][j];
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (button.getText().toString().equals("")) {
                                button.setText(currentChar);
                                button.setTextColor(Color.BLACK);
                                String result = findWinner(board);
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
                                String probableWinner = findProbableWinner(board);
                                String text = probableWinner + " has a higher chance to win!";
                                textViewProb.setText(text);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String result = findWinner(board);
                                        if (result.equals("no winner yet"))
                                            doNextMove(board, currentChar);
                                        String probableWinner = findProbableWinner(board);
                                        String text = probableWinner + " has a higher chance to win!";
                                        textViewProb.setText(text);

                                        result = findWinner(board);
                                        if (!result.equals("no winner yet")) {
                                            textViewResult.setText(result);
                                            textViewResult.setVisibility(View.VISIBLE);
                                        } else {
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
                                    }
                                }, 1000);
                            }
                        }
                    });
                }
            }
        }
    }

    private void doNextMove(Button[][] board, String currentChar) {
        String oppositeChar = (currentChar.equals("x")) ? "o" : "x";
        //rows - win
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().toString().equals(currentChar) &&
                    board[i][1].getText().toString().equals(currentChar) &&
                    !board[i][2].getText().toString().equals(oppositeChar)) {
                board[i][2].setText(currentChar);
                board[i][2].setTextColor(Color.BLACK);
                return;
            }
            if (board[i][0].getText().toString().equals(currentChar) &&
                    board[i][2].getText().toString().equals(currentChar) &&
                    !board[i][1].getText().toString().equals(oppositeChar)) {
                board[i][1].setText(currentChar);
                board[i][1].setTextColor(Color.BLACK);
                return;
            }
            if (board[i][2].getText().toString().equals(currentChar) &&
                    board[i][1].getText().toString().equals(currentChar) &&
                    !board[i][0].getText().toString().equals(oppositeChar)) {
                board[i][0].setText(currentChar);
                board[i][0].setTextColor(Color.BLACK);
                return;
            }
        }
        //columns - win
        for (int i = 0; i < 3; i++) {
            if (board[0][i].getText().toString().equals(currentChar) &&
                    board[1][i].getText().toString().equals(currentChar) &&
                    !board[2][i].getText().toString().equals(oppositeChar)) {
                board[2][i].setText(currentChar);
                board[2][i].setTextColor(Color.BLACK);
                return;
            } else if (board[0][i].getText().toString().equals(currentChar) &&
                    board[2][i].getText().toString().equals(currentChar) &&
                    !board[1][i].getText().toString().equals(oppositeChar)) {
                board[1][i].setText(currentChar);
                board[1][i].setTextColor(Color.BLACK);
                return;
            }
            if (board[2][i].getText().toString().equals(currentChar) &&
                    board[1][i].getText().toString().equals(currentChar) &&
                    !board[0][i].getText().toString().equals(oppositeChar)) {
                board[0][i].setText(currentChar);
                board[0][i].setTextColor(Color.BLACK);
                return;
            }
        }

        //diameter - win
        if (board[0][0].getText().toString().equals(currentChar) &&
                board[1][1].getText().toString().equals(currentChar) &&
                !board[2][2].getText().toString().equals(oppositeChar)) {
            board[2][2].setText(currentChar);
            board[2][2].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][0].getText().toString().equals(currentChar) &&
                board[2][2].getText().toString().equals(currentChar) &&
                !board[1][1].getText().toString().equals(oppositeChar)) {
            board[1][1].setText(currentChar);
            board[1][1].setTextColor(Color.BLACK);
            return;
        }
        if (board[2][2].getText().toString().equals(currentChar) &&
                board[1][1].getText().toString().equals(currentChar) &&
                !board[0][0].getText().toString().equals(oppositeChar)) {
            board[0][0].setText(currentChar);
            board[0][0].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][2].getText().toString().equals(currentChar) &&
                board[1][1].getText().toString().equals(currentChar) &&
                !board[2][0].getText().toString().equals(oppositeChar)) {
            board[2][0].setText(currentChar);
            board[2][0].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][2].getText().toString().equals(currentChar) &&
                board[2][0].getText().toString().equals(currentChar) &&
                !board[1][1].getText().toString().equals(oppositeChar)) {
            board[1][1].setText(currentChar);
            board[1][1].setTextColor(Color.BLACK);
            return;
        }
        if (board[2][0].getText().toString().equals(currentChar) &&
                board[1][1].getText().toString().equals(currentChar) &&
                !board[0][2].getText().toString().equals(oppositeChar)) {
            board[0][2].setText(currentChar);
            board[0][2].setTextColor(Color.BLACK);
            return;
        }

        //rows - not lose
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().toString().equals(oppositeChar) &&
                    board[i][1].getText().toString().equals(oppositeChar) &&
                    !board[i][2].getText().toString().equals(currentChar)) {
                board[i][2].setText(currentChar);
                board[i][2].setTextColor(Color.BLACK);
                return;
            }
            if (board[i][0].getText().toString().equals(oppositeChar) &&
                    board[i][2].getText().toString().equals(oppositeChar) &&
                    !board[i][1].getText().toString().equals(currentChar)) {
                board[i][1].setText(currentChar);
                board[i][1].setTextColor(Color.BLACK);
                return;
            }
            if (board[i][2].getText().toString().equals(oppositeChar) &&
                    board[i][1].getText().toString().equals(oppositeChar) &&
                    !board[i][0].getText().toString().equals(currentChar)) {
                board[i][0].setText(currentChar);
                board[i][0].setTextColor(Color.BLACK);
                return;
            }
        }
        //columns - not lose
        for (int i = 0; i < 3; i++) {
            if (board[0][i].getText().toString().equals(oppositeChar) &&
                    board[1][i].getText().toString().equals(oppositeChar) &&
                    !board[2][i].getText().toString().equals(currentChar)) {
                board[2][i].setText(currentChar);
                board[2][i].setTextColor(Color.BLACK);
                return;
            } else if (board[0][i].getText().toString().equals(oppositeChar) &&
                    board[2][i].getText().toString().equals(oppositeChar) &&
                    !board[1][i].getText().toString().equals(currentChar)) {
                board[1][i].setText(currentChar);
                board[1][i].setTextColor(Color.BLACK);
                return;
            }
            if (board[2][i].getText().toString().equals(oppositeChar) &&
                    board[1][i].getText().toString().equals(oppositeChar) &&
                    !board[0][i].getText().toString().equals(currentChar)) {
                board[0][i].setText(currentChar);
                board[0][i].setTextColor(Color.BLACK);
                return;
            }
        }
        //diameter - not lose
        if (board[0][0].getText().toString().equals(oppositeChar) &&
                board[1][1].getText().toString().equals(oppositeChar) &&
                !board[2][2].getText().toString().equals(currentChar)) {
            board[2][2].setText(currentChar);
            board[2][2].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][0].getText().toString().equals(oppositeChar) &&
                board[2][2].getText().toString().equals(oppositeChar) &&
                !board[1][1].getText().toString().equals(currentChar)) {
            board[1][1].setText(currentChar);
            board[1][1].setTextColor(Color.BLACK);
            return;
        }
        if (board[2][2].getText().toString().equals(oppositeChar) &&
                board[1][1].getText().toString().equals(oppositeChar) &&
                !board[0][0].getText().toString().equals(currentChar)) {
            board[0][0].setText(currentChar);
            board[0][0].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][2].getText().toString().equals(oppositeChar) &&
                board[1][1].getText().toString().equals(oppositeChar) &&
                !board[2][0].getText().toString().equals(currentChar)) {
            board[2][0].setText(currentChar);
            board[2][0].setTextColor(Color.BLACK);
            return;
        }
        if (board[0][2].getText().toString().equals(oppositeChar) &&
                board[2][0].getText().toString().equals(oppositeChar) &&
                !board[1][1].getText().toString().equals(currentChar)) {
            board[1][1].setText(currentChar);
            board[1][1].setTextColor(Color.BLACK);
            return;
        }
        if (board[2][0].getText().toString().equals(oppositeChar) &&
                board[1][1].getText().toString().equals(oppositeChar) &&
                !board[0][2].getText().toString().equals(currentChar)) {
            board[0][2].setText(currentChar);
            board[0][2].setTextColor(Color.BLACK);
            return;
        }

        //no matter
        Random random = new Random();
        int x, y;
        while (true) {
            x = random.nextInt(3);
            y = random.nextInt(3);
            if (board[x][y].getText().toString().equals("")) {
                board[x][y].setText(currentChar);
                board[x][y].setTextColor(Color.BLACK);
                return;
            }
        }
    }

    String findWinner(Button[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().toString().equals(board[i][1].getText().toString())
                    && board[i][2].getText().toString().equals(board[i][1].getText().toString())
                    && !board[i][1].getText().toString().equals(""))
                return board[i][1].getText().toString() + " won!";


            if (board[0][i].getText().toString().equals(board[1][i].getText().toString())
                    && board[2][i].getText().toString().equals(board[1][i].getText().toString())
                    && !board[1][i].getText().toString().equals(""))
                return board[1][i].getText().toString() + " won!";
        }

        if (board[0][0].getText().toString().equals(board[2][2].getText().toString())
                && board[1][1].getText().toString().equals(board[2][2].getText().toString())
                && !board[2][2].getText().toString().equals(""))
            return board[2][2].getText().toString() + " won!";

        if (board[0][2].getText().toString().equals(board[2][0].getText().toString())
                && board[1][1].getText().toString().equals(board[2][0].getText().toString())
                && !board[2][0].getText().toString().equals(""))
            return board[2][0].getText().toString() + " won!";


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().toString().equals("")) return "no winner yet";
            }
        }

        return "Draw!";
    }

    private String findProbableWinner(Button[][] board) {
        int xRows, oRows, xColumns, oColumns, xD1, oD1, xD2, oD2;
        int xWin = 0, oWin = 0;
        //rows
        for (int i = 0; i < 3; i++) {
            xRows = 0;
            oRows = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().toString().equals("x")) xRows++;
                if (board[i][j].getText().toString().equals("o")) oRows++;
            }
            xWin = (xRows == 2 && oRows == 0) ? xWin + 1 : xWin;
            oWin = (oRows == 2 && xRows == 0) ? oWin + 1 : oWin;
        }
        //columns
        for (int i = 0; i < 3; i++) {
            xColumns = 0;
            oColumns = 0;
            for (int j = 0; j < 3; j++) {
                if (board[j][i].getText().toString().equals("x")) xColumns++;
                if (board[j][i].getText().toString().equals("o")) oColumns++;
            }
            xWin = (xColumns == 2 && oColumns == 0) ? xWin + 1 : xWin;
            oWin = (oColumns == 2 && xColumns == 0) ? oWin + 1 : oWin;
        }

        xD1 = 0;
        oD1 = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][i].getText().toString().equals("x")) xD1++;
            if (board[i][i].getText().toString().equals("o")) oD1++;
        }
        xWin = (xD1 == 2 && oD1 == 0) ? xWin + 1 : xWin;
        oWin = (oD1 == 2 && xD1 == 0) ? oWin + 1 : oWin;

        xD2 = 0;
        oD2 = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i].getText().toString().equals("x")) xD2++;
            if (board[i][2 - i].getText().toString().equals("o")) oD2++;
        }
        xWin = (xD2 == 2 && oD2 == 0) ? xWin + 1 : xWin;
        oWin = (oD2 == 2 && xD2 == 0) ? oWin + 1 : oWin;

        if (xWin > oWin) return "x";
        if (oWin > xWin) return "o";

        if (xWin == 0) return "No one";
        return (currentChar.equals("x") ? "o" : "x");
    }
}
