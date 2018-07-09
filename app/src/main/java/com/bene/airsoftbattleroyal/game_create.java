package com.bene.airsoftbattleroyal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Helper.MqttHelper;

public class game_create extends AppCompatActivity {
    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_create);
    }
}
