package com.example.handcontroller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import android.bluetooth.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextActivity extends AppCompatActivity{


    static int erCount=0;
    Button TransBut;
    EditText TrTxt;
    TextView Click;
    static int res;
    private static String mac="98:D3:31:F4:6F:4E";
    BluetoothSocket clientSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        erCount=0;
        setContentView(R.layout.activity_text);
        TrTxt=(EditText)findViewById(R.id.textToTrans);
        TransBut=(Button)findViewById(R.id.translateBut);
        Click=(TextView)findViewById(R.id.TClick);
        TransBut.setOnClickListener(TransButListener);
        TrTxt.setOnEditorActionListener(EnterButListener);
        Click.setOnTouchListener(ClickListener);


        Runnable runnable =new Runnable() {
            @Override
            public void run() {

                if (mac.equals("00:00:00:00:00:00") || mac.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "У вас не настроен mac!", Toast.LENGTH_LONG).show();
                } else {
                    try {
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
                    else
                        if(erCount!=0){
                        Log.i("***Thread***", "Hand have not connected" + erCount);
                      handConEr.sendEmptyMessage(0);}
                }


            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


    }


    View.OnClickListener TransButListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            String text = TrTxt.getText().toString();

            if (res == 0)
                Toast.makeText(getApplicationContext(), "Подключение отсутствует!", Toast.LENGTH_LONG).show();

            if (res != 0) {


                if (CheckWord(text)) {
                    byte[] b = new byte[text.length()];
                    for (int i = 0; i < text.length(); i++) {
                        b[i] = (byte) (text.charAt(i));

                    }
                        try {
                            OutputStream OutStream = clientSocket.getOutputStream();
                            for (int i = 0; i < b.length; i++) {
                                OutStream.write(b[i]);
                            }

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Ошибка отправки!", Toast.LENGTH_LONG).show();
                            Log.d("BLUETOOTH", e.getMessage());
                            erCount++;
                        }
                    if (erCount==0)
                        Toast.makeText(getApplicationContext(), "Отправил:" + text, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Цифры / числа отправлять нельзя!", Toast.LENGTH_LONG).show();
            }
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


    TextView.OnEditorActionListener EnterButListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                Blutooth.startSocket(getApplicationContext());
                return true;
            }

            return false;
        }
    };

   View.OnTouchListener ClickListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction()==MotionEvent.ACTION_DOWN){
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
               //Blutooth.keybClose(imm);
                return true;
            }
            return false;
        }
    };


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


