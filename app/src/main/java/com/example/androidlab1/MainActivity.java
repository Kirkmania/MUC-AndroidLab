package com.example.androidlab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickUpdate(View view) {
        TextView currentgreeting = (TextView) findViewById(R.id.textView);
        Spinner greetings = (Spinner) findViewById(R.id.spinner);
        currentgreeting.setText(greetings.getSelectedItem().toString());
    }
}/**/