package com.example.handcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ManualActivity extends AppCompatActivity {
    SeekBar First;
    SeekBar Second;
    SeekBar Third;
    SeekBar Fourth;
    SeekBar Fifth;
    TextView FirT;
    TextView SecT;
    TextView ThiT;
    TextView FouT;
    TextView FifT;
    Button TransmitBut;
    BluetoothSocket clientSocket;
    int res=0;
    int erCount=0;
    int minVal=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        erCount=0;
        setContentView(R.layout.activity_manual);
        First=(SeekBar)findViewById(R.id.Fir);
        Second=(SeekBar)findViewById(R.id.Sec);
        Third=(SeekBar)findViewById(R.id.Thi);
        Fourth=(SeekBar)findViewById(R.id.Fou);
        Fifth =(SeekBar)findViewById(R.id.Fiv);
        FirT=(TextView)findViewById(R.id.FirT);
        SecT=(TextView)findViewById(R.id.SecT);
        ThiT=(TextView)findViewById(R.id.ThiT);
        FouT=(TextView)findViewById(R.id.FouT);
        FifT =(TextView)findViewById(R.id.FifT);
        TransmitBut=(Button)findViewById(R.id.manualTransBut);
        First.setOnSeekBarChangeListener(Listener);
        Second.setOnSeekBarChangeListener(Listener);
        Third.setOnSeekBarChangeListener(Listener);
        Fourth.setOnSeekBarChangeListener(Listener);
        Fifth.setOnSeekBarChangeListener(Listener);
        TransmitBut.setOnClickListener(TransmitListener);

        Runnable runnable =new Runnable() {
            @Override
            public void run() {

                if (Blutooth.Mac.equals("00:00:00:00:00:00") || Blutooth.Mac.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "У вас не настроен mac!", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                        startActivityForResult(new Intent(enableBT), 0);
                        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice device = bluetooth.getRemoteDevice(Blutooth.Mac);
                        //Инициируем соединение с устройством
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        clientSocket = (BluetoothSocket) m.invoke(device, 1);
                        clientSocket.connect();
                        res++;
                        try {
                            OutputStream OutStream = clientSocket.getOutputStream();
                            OutStream.write(0);
                        }catch (IOException e) {
                            Log.d(" BLUETOOTH", e.getMessage());
                        }


                        //В случае появления любых ошибок, выводим в лог сообщение
                    } catch (IOException e) {
                        erCount++;
                        Log.d("BLUETOOTH", e.getMessage());
                        handConEr.sendEmptyMessage(0); //Отправляем тост на экран приложения,
                    } catch (SecurityException e) {         // используя систему передачи данных между потоками
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
                    if(!(erCount==0)){
                        Log.i("***Thread***", "Hand have not connected" + erCount);
                        handConEr.sendEmptyMessage(0);}
                }


            }
        };

        Thread thread = new Thread(runnable);
        thread.start();



    }

    SeekBar.OnSeekBarChangeListener Listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            getData();
            updateStatus(getData());

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            getData();
            updateStatus(getData());
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            getData();
            updateStatus(getData());

        }
    };

    View.OnClickListener TransmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {




            if (res == 0)
                Toast.makeText(getApplicationContext(), "Подключение отсутствует!", Toast.LENGTH_LONG).show();

            if (res != 0) {

                    byte[] b = new byte[getData().length];
                    for (int i = 0; i < getData().length; i++) {
                        b[i] = (byte) getData()[i];
                    }


                        try {
                            OutputStream OutStream = clientSocket.getOutputStream();
                            for (int i=0; i<b.length; i++) {
                                OutStream.write(b[i]);
                            }


                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Ошибка отправки!", Toast.LENGTH_LONG).show();
                            Log.d("BLUETOOTH", e.getMessage());
                        }


                    Toast.makeText(getApplicationContext(), "Отправил" , Toast.LENGTH_LONG).show();
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


    private int[] getData(){
        int[] fin= {
                First.getProgress()+minVal,
                Second.getProgress()+minVal,
                Third.getProgress()+minVal,
                Fourth.getProgress()+minVal,
                Fifth.getProgress()+minVal,
        };

        return fin;
    }

    public void updateStatus(int[] a){

        FirT.setText(getString(R.string.thumb)+", "+String.valueOf(a[0]));
        SecT.setText(getString(R.string.index)+", "+String.valueOf(a[1]));
        ThiT.setText(getString(R.string.middle)+", "+String.valueOf(a[2]));
        FouT.setText(getString(R.string.ring)+", "+String.valueOf(a[3]));
        FifT.setText(getString(R.string.pinky)+", "+String.valueOf(a[4]));
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
