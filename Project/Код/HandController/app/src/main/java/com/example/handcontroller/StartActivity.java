package com.example.handcontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    Button TextBut;
    Button SpeechBut;
    Button ManualBut;
    Button ExitBut;
    TextView HeadText;
    TextView UnderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    TextBut = (Button)findViewById(R.id.TranslateBut);
    ManualBut =(Button)findViewById(R.id.ManualBut);
    SpeechBut = (Button)findViewById(R.id.SpeechBut);
    ExitBut = (Button)findViewById(R.id.ExitBut);
    HeadText = (TextView)findViewById(R.id.HeadText);
    UnderText = (TextView)findViewById(R.id.UnderText);
    TextBut.setOnClickListener(TextButListener);
    SpeechBut.setOnClickListener(SpeechButListener);
    ManualBut.setOnClickListener(ManualButListener);
    ExitBut.setOnClickListener(ExitButListener);
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
            startActivityForResult(new Intent(enableBT), 0);
        }
    };
    Thread thread = new Thread(runnable);
    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

    if(!bluetooth.isEnabled())
        thread.start();

    }

    View.OnClickListener TextButListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent TextActivityIntent = new Intent(StartActivity.this, TextActivity.class);
            startActivity(TextActivityIntent);

        }
    };


    View.OnClickListener SpeechButListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent VoiceActivityIntent=new Intent(StartActivity.this, VoiceActivity.class);
            startActivity(VoiceActivityIntent);
        }
    };

    View.OnClickListener ManualButListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent ManualActivityIntent = new Intent(getApplicationContext(), ManualActivity.class);
            startActivity(ManualActivityIntent);

        }
    };

    View.OnClickListener ExitButListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                finish();
        }
    };
}
