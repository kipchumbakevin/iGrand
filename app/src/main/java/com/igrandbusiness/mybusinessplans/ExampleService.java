package com.igrandbusiness.mybusinessplans;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import static com.igrandbusiness.mybusinessplans.App.CHANNEL_ID;


public class ExampleService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("TITLE")+" now playing";

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        Intent intentCancel= new Intent(this,Podcasts.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this,0,intentCancel, 0);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(title)
                .setContentText("Click to stop")
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(bitmap)
                .setContentIntent(mPendingIntent)
                .build();

        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
