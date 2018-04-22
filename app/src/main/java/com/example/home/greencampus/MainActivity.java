package com.example.home.greencampus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.home.greencampus.adapter.FragmentChanger;

public class MainActivity extends AppCompatActivity implements FragmentChanger {

    public static String ipAdd;
    public static int ipPort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.mainFrame,EstablishConnection.newInstance(),"check connection fragment").
                    addToBackStack(null).
                    commit();
        }
    }

    @Override
    public void changeToSensorDetails(String ip, int port) {
        ipAdd = ip;
        ipPort = port;

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.mainFrame,SensorValues.newInstance(ip,port),"changing to alarm page").
                addToBackStack(null).
                commit();

    }

    public void vncviewerview(){
        Toast.makeText(this,"Switching to VNC",Toast.LENGTH_SHORT).show();
        Intent launchIntent =  this.getPackageManager().getLaunchIntentForPackage("com.realvnc.viewer.android");
        startActivity(launchIntent);
    }
}
