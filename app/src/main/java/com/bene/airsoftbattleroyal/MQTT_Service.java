package com.bene.airsoftbattleroyal;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

abstract class MQTT_Service extends Service {

    private static final String TAG = "TAG";
    final String serverUri = "tcp://m12.cloudmqtt.com:11111";

    public MQTT_Service() {
    }
    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        //Connect to Server onCreate
        Bundle bundle = intent.getExtras();
        String user = bundle.getString("USERNAME");
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(MQTT_Service.this, serverUri, clientId);

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
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }

    public void sendMessage(String payload){
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
