package com.example.nikhilsingh.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
/**
 * Created by Nikhil.Singh on 17-Feb-16.
 */
public class BTStateChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                -1);

        switch(state){
            case BluetoothAdapter.STATE_CONNECTED:
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                // Setting Dialog Title
                alertDialog.setTitle("Message");
                // Setting Dialog Message
                alertDialog.setMessage("Bluetooth connected");

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Showing Alert Message
                alertDialog.show();
//                Toast.makeText(context,
//                        "BTStateChangedBroadcastReceiver: STATE_CONNECTED",
//                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_CONNECTING:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_CONNECTING",
                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_DISCONNECTED:
                AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
                // Setting Dialog Title
                alertDialog1.setTitle("Message");
                // Setting Dialog Message
                alertDialog1.setMessage("Bluetooth connected");

                // Setting OK Button
                alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Showing Alert Message
                alertDialog1.show();
//                Toast.makeText(context,
//                        "BTStateChangedBroadcastReceiver: STATE_DISCONNECTED",
//                        Toast.LENGTH_SHORT).show();
//                break;
            case BluetoothAdapter.STATE_DISCONNECTING:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_DISCONNECTING",
                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_OFF:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_OFF",
                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_ON:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_ON",
                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_TURNING_OFF",
                        Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Toast.makeText(context,
                        "BTStateChangedBroadcastReceiver: STATE_TURNING_ON",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
