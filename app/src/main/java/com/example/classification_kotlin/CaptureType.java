package com.example.classification_kotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CaptureType extends AppCompatActivity {

    private Button Plants;
    private Button Flowers;
    private Button SPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_type);

        Plants = (Button) (findViewById(R.id.plant));
        Flowers = (Button) (findViewById(R.id.flower));
        SPlace = (Button) (findViewById(R.id.places));

        Plants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureType.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureType.this, FlowerCapture.class);
                startActivity(intent);
            }
        });

        SPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureType.this, SpecialPlace.class);
                startActivity(intent);
            }
        });

    }
}