package com.example.xo_table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChoiceActivity extends AppCompatActivity {
    RadioGroup modeGroup;
    RadioGroup shapeGroup;
    RadioButton modeButton;
    RadioButton shapeButton;
    String modeText = "";
    String shapeText = "";
    Intent intent;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        intent = new Intent(ChoiceActivity.this, MainActivity.class);
        modeGroup = findViewById(R.id.modeRadioGroup);
        shapeGroup = findViewById(R.id.shapeRadioGroup);
        playButton = findViewById(R.id.btn_play);

        modeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            modeButton = (RadioButton) findViewById(checkedId);
            modeText = modeButton.getText().toString();
        });

        shapeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            shapeButton = (RadioButton) findViewById(checkedId);
            shapeText = shapeButton.getText().toString();
        });

        playButton.setOnClickListener(v -> {
            if (modeText.equals("")) {
                Toast.makeText(getBaseContext(), "You have to choose the mode first."
                        , Toast.LENGTH_LONG).show();
            }
            else if (shapeText.equals("")) {
                Toast.makeText(getBaseContext(), "You have to choose the shape first."
                        , Toast.LENGTH_LONG).show();
            }
            else {
                intent.putExtra("mode", modeText);
                intent.putExtra("shape", shapeText);
                startActivity(intent);
            }
        });
    }
}