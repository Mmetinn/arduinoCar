package com.example.mehme.arduinoaraba;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.mehme.arduinoaraba.kontrollerActivity.myUUID;

public class kontrollerActivity extends AppCompatActivity {
    Button ileriBtn,geriBtn,disableBtn,sagBtn,solBtn;
    String address;
    Button btnOn, btnOff;
    TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
    Handler bluetoothIn;

    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread  mConnectedThread;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    SeekBar pwm;
    // String for MAC address
    int speed;

    ImageView iv;

    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontroller);
        ileriBtn=(Button)findViewById(R.id.ileri);
        geriBtn=(Button)findViewById(R.id.geri);
        sagBtn=(Button)findViewById(R.id.sag);
        solBtn=(Button)findViewById(R.id.sol);
        iv=(ImageView)findViewById(R.id.imageView);



        MobileAds.initialize(this,"ca-app-pub-7334446571364171~2263897967");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        Toast.makeText(getBaseContext(), "BAĞLANTI BAŞARILI", Toast.LENGTH_SHORT).show();
        pwm = (SeekBar) findViewById(R.id.pwm);
        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS);
        speed=0;
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();                          //get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            sensorView0.setText(" Sensor 0 Voltage = " + sensor0 + "V");    //update the textviews with sensor values
                            sensorView1.setText(" Sensor 1 Voltage = " + sensor1 + "V");
                            sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
                            sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        ileriBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("F");    // Send "1" via Bluetooth
                    // Do what you want
                    iv.setImageResource(R.drawable.yukari);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("S");    // Send "1" via Bluetooth
                    iv.setImageResource(R.drawable.dur);
                    // Do what you want
                    return true;
                }

                return false;
            }
        });
        geriBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("geri","geldi");
                    mConnectedThread.write("B");    // Send "0" via Bluetooth
                    iv.setImageResource(R.drawable.asagi);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("S");    // Send "1" via Bluetooth
                    iv.setImageResource(R.drawable.dur);
                    // Do what you want
                    return true;
                }

                return false;
            }
        });
        sagBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("L");    // Send "1" via Bluetooth
                    iv.setImageResource(R.drawable.saga);
                    // Do what you want
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("S");    // Send "1" via Bluetooth
                    iv.setImageResource(R.drawable.dur);
                    // Do what you want
                    return true;
                }

                return false;
            }
        });
        solBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("geri","geldi");
                    mConnectedThread.write("R");    // Send "0" via Bluetooth
                    iv.setImageResource(R.drawable.sola);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("ileri","geldi");
                    mConnectedThread.write("S");    // Send "1" via Bluetooth
                    iv.setImageResource(R.drawable.dur);
                    // Do what you want
                    return true;
                }

                return false;
            }
        });

        pwm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if(speed <= 25) {
                    mConnectedThread.write("1");}
                else if(speed <= 50) {
                    mConnectedThread.write("2");}
                else if(speed <= 75) {
                    mConnectedThread.write("3");}
                else if(speed <= 100) {
                    mConnectedThread.write("4");}
                else if(speed <= 125) {
                    mConnectedThread.write("5");}
                else if(speed <= 150) {
                    mConnectedThread.write("6");}
                else if(speed <= 175) {
                    mConnectedThread.write("7");}
                else if(speed <= 200) {
                    mConnectedThread.write("8");}
                else if(speed <= 250) {
                    mConnectedThread.write("9");}


                //bl.sendData(String.valueOf(speedLetter));

            }

        });


    }

    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }


    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }


}

/*package com.example.mehme.arduinoaraba;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.io.IOException;
import java.util.UUID;

import static com.example.mehme.arduinoaraba.kontrollerActivity.myUUID;

public class kontrollerActivity extends AppCompatActivity {
    Button ileriBtn,geriBtn,disableBtn;
    String address;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private ProgressDialog progress;

    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontroller);
        ileriBtn=(Button)findViewById(R.id.ileri);
        geriBtn=(Button)findViewById(R.id.geri);
        disableBtn=(Button)findViewById(R.id.disable);
        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS);
        new ConnectBT().execute();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(kontrollerActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Lütfen bağlanmak istediğiniz bluetooth cihazinin açık oluduğundan emi olun.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    public void ileriClicked(View v){
        Log.d("ileri","geldi");
        Toast.makeText(getApplicationContext(),"ileri",Toast.LENGTH_SHORT);
        turnOnLed();
    }
    public void geriClicked(View v){
        Log.d("geri","geldi");
        Toast.makeText(getApplicationContext(),"geri",Toast.LENGTH_SHORT);
        turnOffLed();
    }

    public void disableClicked(View v){
        Log.d("disable","geldi");
        Toast.makeText(getApplicationContext(),"diconnect",Toast.LENGTH_SHORT);
        Disconnect();
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            Log.d("disconnect fonk","geldi");
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout
    }

    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            Log.d("off","geldi");
            try
            {
                Log.d(String.valueOf(btSocket.hashCode()),"cikti1");
                btSocket.getOutputStream().write(1);

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            Log.d("on","geldi");
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());
                //btSocket.getOutputStream().write(1);
            }
            catch (IOException e)
            {
                msg("Error");
            }

        }
    }
}
*/