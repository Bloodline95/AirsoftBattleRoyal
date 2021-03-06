package com.bene.airsoftbattleroyal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    MqttHelper mqttHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spiel erstellen
        final Button btn_erstellen = findViewById(R.id.button_erstellen);
        btn_erstellen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = "";
                TextView nutzername = findViewById(R.id.editText);
                username = nutzername.getText().toString();

                if(!username.equals("")) {
                    mqttHelper.set_username_and_password(username, "1234");
                    startMqtt();
                    Intent erstellen_intent = new Intent(MainActivity.this, game_create.class);
                    startActivity(erstellen_intent);
                }
                else{
                    nutzername.setError("Nutzername eingeben");
                }
            }
        });

        //Spiel beitreten
        final Button btn_beitreten = findViewById(R.id.button_beitreten);
        btn_beitreten.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = "";
                TextView nutzername = findViewById(R.id.editText);
                username = nutzername.getText().toString();

                if(!username.equals("")) {
                    mqttHelper.set_username_and_password(username, "1234");
                    startMqtt();
                    Intent beitreten_intent = new Intent(MainActivity.this, game_choose.class);
                    beitreten_intent.putExtra("USERNAME", username);
                    startActivity(beitreten_intent);
                }
                else{
                    nutzername.setError("Nutzername eingeben");
                }
            }
        });

    }


    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });

    }
}
