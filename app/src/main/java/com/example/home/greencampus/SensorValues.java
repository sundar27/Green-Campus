package com.example.home.greencampus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.greencampus.adapter.FragmentChanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by home on 4/3/18.
 */

public class SensorValues extends Fragment {
    Context mcontext;
    FragmentChanger fragmentChanger;
    View view;
    private static String ip_add;
    private static int ip_port;
    public static TextView temp;
    public static TextView hum;
    public static String input="";
    Button button;
    Socket clientSocket;
    public static SensorValues newInstance(String ip,int port){
        ip_add = ip;
        ip_port=port;
        return new SensorValues();
    }

    @Override
    public void onAttach(Context context) {
        mcontext=context;
        super.onAttach(context);
        if(context instanceof FragmentChanger){
            fragmentChanger = (FragmentChanger)context;
        }
        else{
            throw new ClassCastException(context.toString()+" must implement listener");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("mytest", "oncreate entered");
        view = inflater.inflate(R.layout.frame_sensor_values, container, false);
        temp = (TextView) view.findViewById(R.id.temp_value);
        hum = (TextView)view.findViewById(R.id.hum_value);
        button = (Button)view.findViewById(R.id.rdval);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readValues();
            }
        });
        return view;
    }

    public void readValues(){
        Toast.makeText(getActivity(),"button clicked",Toast.LENGTH_LONG).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(ip_add,ip_port);

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    SensorValues.input = buffer.readLine();

                    buffer.close();
                    clientSocket.close();
                    Log.d("mytest","success");

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
        while (input.equals("")){
        }
        Toast.makeText(getActivity(),input.split("\\s+")[2]+input.split("\\s+")[5],Toast.LENGTH_LONG).show();

        SensorValues.temp.setText(input.split("\\s+")[2].toString());
        SensorValues.hum.setText(input.split("\\s+")[5].toString());
        SensorValues.input="";
    }
}




