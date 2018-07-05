package com.bene.airsoftbattleroyal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Spiel erstellen
        final Button btn_erstellen = findViewById(R.id.button_erstellen);
        btn_erstellen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent erstellen_intent = new Intent(MainActivity.this, game_create.class);
                startActivity(erstellen_intent);
            }
        });

        //Spiel beitreten
        final Button btn_beitreten = findViewById(R.id.button_beitreten);
        btn_beitreten.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username;
                TextView nutzername = findViewById(R.id.editText);
                username = nutzername.getText().toString();

                Intent intent = new Intent(MainActivity.this, MQTT_Service.class);
                intent.putExtra("USERNAME", username);
                startService(intent);

                Intent beitreten_intent = new Intent(MainActivity.this, game_choose.class);
                beitreten_intent.putExtra("USERNAME", username);
                startActivity(beitreten_intent);
            }
        });

    }

    

}
