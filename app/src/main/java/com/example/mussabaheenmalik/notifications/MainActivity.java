package com.example.mussabaheenmalik.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotifyManager;
    NotificationManagerCompat mymanger;
    private static final int NOTIFICATION_ID = 0;
    PendingIntent notification;
    NotificationCompat.Builder mynoti;
    Button b1,b2;
    NotificationReceiver mReceiver = new NotificationReceiver();
    private static final String ACTION_CANCEL_NOTIFICATION =
            "com.example.android.notifyme.ACTION_CANCEL_NOTIFICATION";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
         mNotifyManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mymanger = NotificationManagerCompat.from(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button3);
        b2 = (Button)findViewById(R.id.button);
        b1.setEnabled(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);

    }

    public void notify(View view) {
         mynoti = new NotificationCompat.Builder(this);
        mynoti.setContentTitle("NEW MESSAGE ! ");
        mynoti.setContentText("Hi ! How are you ?");
        mynoti.setSmallIcon(android.R.drawable.button_onoff_indicator_off);
        Intent i1 = new Intent(this,MainActivity.class);
        PendingIntent pd = PendingIntent.getActivities(this,1, new Intent[]{i1},0);
        mynoti.setContentIntent(pd);
        mynoti.setAutoCancel(true);
        mymanger.notify(1,mynoti.build());
        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, 1, cancelIntent, PendingIntent.FLAG_ONE_SHOT);
        mynoti.setDeleteIntent(cancelPendingIntent);
        mymanger.notify(1,mynoti.build());
        b1.setEnabled(true);
        b2.setEnabled(false);
    }

    public void cancel(View view) {
            mymanger.cancel(1);
            b1.setEnabled(false);
            b2.setEnabled(true);


    }
    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==ACTION_CANCEL_NOTIFICATION) {
                Toast.makeText(context, "NOTIFICATION CLOSED !", Toast.LENGTH_SHORT).show();
                b1.setEnabled(false);
                b2.setEnabled(true);
            }

        }
    }
}
