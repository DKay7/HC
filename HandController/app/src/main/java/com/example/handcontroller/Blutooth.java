package com.example.handcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.crypto.Mac;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class Blutooth {

    public static String Mac="98:D3:31:F4:6F:4E";

    public  static void startSocket(final Context context) {
        Runnable runnable =new Runnable() {
            @Override
            public void run() {

                if (Blutooth.Mac.equals("00:00:00:00:00:00") || Blutooth.Mac.equals(" ")) {
                    Toast.makeText(context, "У вас не настроен mac!", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        BluetoothSocket clientSocket;
                       // String enableBT = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                        //startActivityForResult(new Intent(enableBT), 0);
                        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice device = bluetooth.getRemoteDevice(Blutooth.Mac);
                        //Инициируем соединение с устройством
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        clientSocket = (BluetoothSocket) m.invoke(device, 1);
                        clientSocket.connect();
                        //res++;
                        try {
                            OutputStream OutStream = clientSocket.getOutputStream();
                            OutStream.write(0);
                        }catch (IOException e) {
                            Log.d(" BLUETOOTH", e.getMessage());
                        }


                        //В случае появления любых ошибок, выводим в лог сообщение
                    } catch (IOException e) {
                       // erCount++;
                        Log.d("BLUETOOTH", e.getMessage());
                        //handConEr.sendEmptyMessage(0); //Отправляем тост на экран приложения,
                    } catch (SecurityException e) {         // используя систему передачи данных между потоками
                        //erCount++;
                        //handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (NoSuchMethodException e) {
                        //erCount++;
                       // handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalArgumentException e) {
                       // erCount++;
                       // handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (IllegalAccessException e) {
                        //erCount++;
                        //handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    } catch (InvocationTargetException e) {
                       // erCount++;
                       // handConEr.sendEmptyMessage(0);
                        Log.d("BLUETOOTH", e.getMessage());
                    }
                    //if (erCount==0) {
                        Log.i("***Thread***", "Hand successfully connected");
                        //handConOk.sendEmptyMessage(0);
                    //}
                    //else
                    //if(!(erCount==0)){
                        //Log.i("***Thread***", "Hand have not connected" + erCount);
                        //handConEr.sendEmptyMessage(0);}
                }


            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

}





}
