package com.example.a1l1.Receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.a1l1.R;

public class InternetReceiver extends BroadcastReceiver {
    private int messageId = 1100;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean missCon = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

        if (missCon) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Оповещение")
                    .setContentText("Отсутствует подключение к сети");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId++, builder.build());
        }
    }
}
