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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.crypto.Mac;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Даня on 26.03.2018.
 */

public class Blootooth{


    public  static void startSocket(Context context) {


            Toast.makeText(context,"Hand successfully connected", Toast.LENGTH_LONG).show();
}



    public static void keybClose(InputMethodManager imm){
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


}
