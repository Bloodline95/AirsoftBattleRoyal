package com.bene.airsoftbattleroyal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;

import Helper.MqttHelper;

public class game_choose extends AppCompatActivity {
    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_choose);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            String username = bundle.getString("USERNAME");
        }
        mqttHelper.subscribe("games");
        start_receive();
    }

    @Override
    protected void onStart(){


        super.onStart();
    }

    public void start_receive(){

    }

}
