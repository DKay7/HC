package com.example.handcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity {
    protected final static int SPEECH_RESULT=1;
    EditText RecTxt;
    Button Rec;
    Button Trans;
    TextView Click;
    private static String mac="98:D3:31:F4:6F:4E";
    BluetoothSocket clientSocket;
    int res=0;
    static int erCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        RecTxt=(EditText)findViewById(R.id.voiceTextToTrans);
        Rec=(Button)findViewById(R.id.voiceBut);
        Trans=(Button)findViewById(R.id.voiceTranslateBut);
        Click=(TextView)findViewById(R.id.VClick);
        RecTxt.setOnEditorActionListener(RecTxtListener);
        Rec.setOnClickListener(RecListener);
        Trans.setOnClickListener(TransListener);
        Click.setOnTouchListener(ClickListener);
        erCount=0;
        Runnable runnable =new Runnable() {
            @Override
            public void run() {

                if (mac.equals("00:00:00:00:00:00") || mac.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "У вас не настроен mac!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                        startActivityForResult(new Intent(enableBT), 0);
                        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice device = bluetooth.getRemoteDevice(mac);
                        //Инициируем соединение с устройством
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        clientSocket = (BluetoothSocket) m.invoke(device, 1);
                        clientSocket.connect();
                        res++;

                        try {//отправляем флаг данных
                            OutputStream OutStream = clientSocket.getOutputStream();
                            OutStream.write(1);
                        }catch (IOException e) {
                            Log.d(" BLUETOOTH", e.getMessage());
                        }


                        //В случае появления любых ошибок, выводим в лог сообщение
                    } catch (IOException e) {
                        erCount++;
                        Log.d("BLUETOOTH", e.getMessage());
                        handConEr.sendEmptyMessage(0);
                    } catch (SecurityException e) {
                        erCount++;
                        handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (NoSuchMethodException e) {
                        erCount++;
                        handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalArgumentException e) {
                        erCount++;
                        handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalAccessException e) {
                        erCount++;
                        handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (InvocationTargetException e) {
                        erCount++;
                        handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    }
                    if (erCount==0) {
                        Log.i("***Thread***", "Hand successfully connected");
                        handConOk.sendEmptyMessage(0);
                    }
                    else {
                        Log.i("***Thread***", "Hand have not connected" + " "+erCount);
                        handConEr.sendEmptyMessage(0);}
                }


            }


        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    TextView.OnEditorActionListener RecTxtListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){

            }

            return false;
        }
    };


    View.OnClickListener RecListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent RecIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            RecIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, getResources().getConfiguration().locale.getLanguage());
            try{
                startActivityForResult(RecIntent, SPEECH_RESULT);
            }
            catch (ActivityNotFoundException e){
                RecTxt.setText(getString(R.string.rec_error));
            }
        }
    };


    View.OnClickListener TransListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String text = RecTxt.getText().toString();
            //Integer k=Integer.parseInt(TrTxt.getText().toString());


            if (res == 0)
                Toast.makeText(getApplicationContext(), "Подключение отсутствует!", Toast.LENGTH_LONG).show();

            if (res != 0) {
                if (CheckWord(text)) {//если введено слово
                    byte[] b = new byte[text.length()];

                    for (int i = 0; i < text.length(); i++) {
                        b[i] = (byte) text.charAt(i);
                    }


                        try {
                            OutputStream OutStream = clientSocket.getOutputStream();
                            for (int i = 0; i < b.length; i++) {
                                OutStream.write(b[i]);
                            }


                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Ошибка отправки!", Toast.LENGTH_LONG).show();
                            Log.d("BLUETOOTH", e.getMessage());
                        }


                    Toast.makeText(getApplicationContext(), "Отправил:" + text, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Цифры / числа отправлять нельзя!", Toast.LENGTH_LONG).show();
            }

        }
    };

    View.OnTouchListener ClickListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction()==MotionEvent.ACTION_DOWN){
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                Blutooth.keybClose(imm);
                return true;
            }
            return false;
        }
    };
    Handler handConOk =new Handler(){
        @Override
        public void handleMessage(Message msg){
            Toast.makeText(getApplicationContext(), ""+getString(R.string.bt_con_ok),Toast.LENGTH_LONG).show();

        }
    };
    Handler handConEr =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), ""+getString(R.string.bt_con_err),Toast.LENGTH_LONG).show();
        }
    };
    Handler handDisConEr =new Handler(){
        @Override
        public void handleMessage(Message msg){
            Toast.makeText(getApplicationContext(), ""+getString(R.string.bt_disCon_err),Toast.LENGTH_LONG).show();

        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RESULT: {
                if (resultCode == RESULT_OK) {

                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    RecTxt.setText(text.get(0));

                }
                else
                    RecTxt.setText(getString(R.string.rec_unheard));
                break;
            }

        }

    }

    private boolean CheckWord(String s){//возвращает false, если введенное значение -- не слово
        if (s!=null&&s.length()!=0){

        for (int i=0; i<s.length(); i++){
        char c;
        c=s.charAt(i);
        if((c>='0'&&c<='9')){
        return false;
        }
        }


        return true;
        }
        else
        return false;
        }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Runnable runDes = new Runnable() {
            @Override
            public void run() {
                try {

                    clientSocket.close();
                    Log.i("***Bluetooth***", "Bluetooth socket successfully close");
                }catch (IOException e){
                    Log.d("***Bluetooth***", "Close socket failed");
                    handDisConEr.sendEmptyMessage(0);
                }
            }
        };
        Thread threadDes =new Thread(runDes);
        threadDes.start();

    }
}
