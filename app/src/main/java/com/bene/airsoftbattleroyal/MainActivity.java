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
                connect(username);
                Intent beitreten_intent = new Intent(MainActivity.this, game_choose.class);
                beitreten_intent.putExtra("USERNAME", username);
                startActivity(beitreten_intent);
            }
        });

    }

    void connect(String user){
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(MainActivity.this, "tcp://broker.hiveqm.com:1883",
                        clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(user);
        try{
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    //connected
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    //something went wrong
                    Log.d(TAG, "onFailure");
                }
            });
        } catch(MqttException e){
            e.printStackTrace();
        }

        String topic = "foo/SQL_inject";
        String payload = "0echo \"UPDATE Spieler SET USERNAME = '" + user + "'\" | mysql -uroot -proot root";
        byte[] encodedPayload = new byte[0];
        try{
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        }catch(UnsupportedEncodingException | MqttException e){
            e.printStackTrace();
        }
    }

}
