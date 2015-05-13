package com.sukinsan.friendlyhome.activity;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sukinsan.friendlyhome.R;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_ACTIVE = "zHeney";
    private SharedPreferences sharedPreferences;
    private ScreenOffOnListener myBroadcast = new ScreenOffOnListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        Switch switchActivity = (Switch)findViewById(R.id.switch_active);
        switchActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableHome();
                } else {
                    disableHome();
                }
                sharedPreferences.edit().putBoolean(KEY_ACTIVE, isChecked).commit();
            }
        });

        boolean isActive = sharedPreferences.getBoolean(KEY_ACTIVE, false);
        switchActivity.setChecked(isActive);
        if(isActive){
            enableHome();
        }
    }

    private void enableHome(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        getApplicationContext().registerReceiver(myBroadcast, intentFilter);
    }

    private void disableHome(){
        getApplicationContext().unregisterReceiver(myBroadcast);
    }
}