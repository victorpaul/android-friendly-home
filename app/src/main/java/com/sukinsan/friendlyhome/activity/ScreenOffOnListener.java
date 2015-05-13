package com.sukinsan.friendlyhome.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOffOnListener extends BroadcastReceiver {
    public ScreenOffOnListener() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startHomescreen.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(startHomescreen);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
