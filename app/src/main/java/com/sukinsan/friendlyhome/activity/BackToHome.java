package com.sukinsan.friendlyhome.activity;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;


import com.sukinsan.friendlyhome.R;

/**
 * Implementation of App Widget functionality.
 */
public class BackToHome extends AppWidgetProvider {
    private static final String TAG = BackToHome.class.getSimpleName();

    private Intent mUpdateService;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mUpdateService == null) {
            mUpdateService = new Intent(context, UpdateService.class);
        }
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_DISABLED)) {
            context.stopService(mUpdateService);
        } else {
            if (mUpdateService == null) {
                mUpdateService = new Intent(context, UpdateService.class);
            }

            context.startService(mUpdateService);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        //context.startService(mUpdateService);
    }

    @Override
    public void onDisabled(Context context) {
        //context.stopService(mUpdateService);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.back_to_home);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static class UpdateService extends Service {
        private BroadcastReceiver mReceiver = new ScreenOffOnListener();

        @Override
        public void onCreate() {
            Log.i(TAG, "created");
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.SCREEN_OFF");
            registerReceiver(mReceiver, filter);
        }

        @Override
        public void onDestroy() {
            try {
                unregisterReceiver(mReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

}