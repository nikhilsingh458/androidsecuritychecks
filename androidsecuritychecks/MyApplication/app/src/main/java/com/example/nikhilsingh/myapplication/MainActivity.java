package com.example.nikhilsingh.myapplication;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.example.nikhilsingh.sclibrary.DeviceCheck;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    final Context context = this;
    boolean isrequired=true;
    public volatile boolean parsingComplete = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btncheckroooted = (Button) findViewById(R.id.checkrooted);
        Button btnEmulatortest = (Button) findViewById(R.id.emulatortest);
        Button btnAppSign = (Button) findViewById(R.id.btnappsignature);
        Button btnbluetooth = (Button) findViewById(R.id.btnbluetoothcheck);
        final StringBuilder sbdata=new StringBuilder();
        final DeviceCheck deviceCheck=new DeviceCheck(false,context);

        btnbluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean bluetoothconnected = deviceCheck.isBluetoothStatusEnabled();

    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    // Setting Dialog Title
    alertDialog.setTitle("Message");
    // Setting Dialog Message
    if(bluetoothconnected) {
        alertDialog.setMessage("Bluetooth Connected.");
    }
    else
    {
        alertDialog.setMessage("Bluetooth is not Connected.");
    }
    // Setting OK Button
    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        }
    });
    // Showing Alert Message
    alertDialog.show();

            }
        });




        //Rooted check
        btncheckroooted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isrooted=deviceCheck.isRooted();
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                // Setting Dialog Title
                alertDialog.setTitle("Message");
                // Setting Dialog Message
                if(isrooted) {
                    alertDialog.setMessage("Device is rooted.Click OK to exit!");
                }
                else
                {
                    alertDialog.setMessage("Device is not rooted.Click OK to exit!");
                }
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
            });

        btnEmulatortest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isemulatorattached= deviceCheck.isEmulatorRunningProcessesDebuggerTest();
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                // Setting Dialog Title
                alertDialog.setTitle("Message");
                // Setting Dialog Message
                if(isemulatorattached) {
                    alertDialog.setMessage("Device is in debug mode.Click OK to exit!");
                }
                else
                {
                    alertDialog.setMessage("Device is not in debug mode.Click OK to exit!");
                }
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }});

        IntentFilter Filter=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(new BTStateChangeReciever(),Filter);

        final BroadcastReceiver mReceiver = new BroadcastReceiver()
        {
            public void onReceive (Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_CONNECTED) {


                        // Bluetooth is disconnected, do handling here
                    }

                }
            }};

        btnAppSign.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String signature=deviceCheck.getSignature();
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                // Setting Dialog Title
                alertDialog.setTitle("Message");
                // Setting Dialog Message
                if(signature !=null || signature != "")
                {
                    alertDialog.setMessage("Signature is " + signature);
                }
                else {
                    alertDialog.setMessage("Signatures not found for the given application");
                }
                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        }
        );


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public ArrayList<String> ReadValues(int resourceId)
    {
        ArrayList<String> values = new ArrayList<String>();
        // The InputStream opens the resourceId and sends it to the buffer
        InputStream is = this.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null)
            {
                Log.d("TEXT", readLine);
                values.add(readLine);
            }

            // Close the InputStream and BufferedReader
            is.close();
            br.close();
            return  values;

        } catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nikhilsingh.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.nikhilsingh.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
