package com.bene.airsoftbattleroyal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class game_choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_choose);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            String username = bundle.getString("USERNAME");
        }

    }

}
