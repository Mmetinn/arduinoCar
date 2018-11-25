package com.example.mehme.arduinoaraba;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView cihazlarList;
    Button ileriBtn;
    private BluetoothAdapter myBluetooth=null;
    private Set<BluetoothDevice> eslenmisCihazlar;
    public static String EXTRA_DEVICE_ADDRESS="device_addres";

    AdView adView,adView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cihazlarList=(ListView)findViewById(R.id.cihazlar);
        ileriBtn=(Button)findViewById(R.id.ileri);


        MobileAds.initialize(this,"ca-app-pub-7334446571364171~2263897967");
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView2=(AdView)findViewById(R.id.adView2);
        adView2.loadAd(adRequest);


        myBluetooth = BluetoothAdapter.getDefaultAdapter ();
        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(),"Bluetooht aygıtı mevcut değil..",Toast.LENGTH_LONG).show();
            finish();
        }else{
            if(myBluetooth.isEnabled()){}
            else{
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon,1);
            }
        }
        ileriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                eslenmisCihazList(); //method that will be called
            }
        });

    }
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {
            ProgressDialog pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Yükleniyor");
            pd.setIcon(R.drawable.progressimage);
            pd.show();

            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
            Intent i = new Intent(MainActivity.this, kontrollerActivity.class);
            //Change the activity.
            i.putExtra(EXTRA_DEVICE_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };
    private void eslenmisCihazList(){
        eslenmisCihazlar=myBluetooth.getBondedDevices();
        ArrayList list=new ArrayList();
        if(eslenmisCihazlar.size()>0){
            for(BluetoothDevice bt : eslenmisCihazlar){
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        }else
        {
            Toast.makeText(getApplicationContext(), "Eşleştirilmiş bluetooth cihazı bulunamadı.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        cihazlarList.setAdapter(adapter);
        cihazlarList.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked
    }
}
