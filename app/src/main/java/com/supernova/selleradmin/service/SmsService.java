package com.supernova.selleradmin.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.view.SplashActivity;

import java.util.Objects;

public class SmsService extends Service {

    private static boolean isRunning = false;

    @Override
    public void onCreate() {

        super.onCreate();

        if (!isRunning) {

            initializeService();
        }
    }

    private void startReceiver() {

        BroadcastReceiver receiver = new SmsReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {

            initializeService();
            startReceiver();
        }

        return START_STICKY;
    }

    private void initializeService() {

        Log.d("Message:Service", "Started");

        isRunning = true;

        Intent notificationIntent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(Constants.NOTIF_CHANNEL_ID, Constants.NOTIF_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_MIN);
            channel.setDescription("Detecting Transactions");

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }

        startForeground(Constants.NOTIF_ID_DEFAULT, new NotificationCompat.Builder(this,
                Constants.NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setContentTitle(Constants.NOTIF_CHANNEL_ID)
                .setContentText("Service is running in background")
                .setSmallIcon(R.drawable.ic_facebook)
                .setContentIntent(pendingIntent)
                .build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}