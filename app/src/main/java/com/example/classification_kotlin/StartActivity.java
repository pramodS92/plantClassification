package com.example.classification_kotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class StartActivity extends AppCompatActivity {

    private RelativeLayout viewGetStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        initUiProps();
        initClickListners();
    }

    private void initUiProps(){
        viewGetStart = (RelativeLayout) (findViewById(R.id.view_get_start));
    }

    private void initClickListners(){
        viewGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, CaptureType.class);
                startActivity(intent);
            }
        });
    }
}