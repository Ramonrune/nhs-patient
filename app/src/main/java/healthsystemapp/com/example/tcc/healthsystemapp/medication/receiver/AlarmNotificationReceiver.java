package healthsystemapp.com.example.tcc.healthsystemapp.medication.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

import healthsystemapp.com.example.tcc.healthsystemapp.R;
import healthsystemapp.com.example.tcc.healthsystemapp.medication.MedicationController;

/**
 * Created by Usuario on 03/09/2018.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent myIntent) {

        long time = myIntent.getLongExtra("TIME", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar settedCalendar = Calendar.getInstance();
        settedCalendar.setTimeInMillis(time);



        System.out.println(calendar.getTimeInMillis() +  "_--------------- " + settedCalendar.getTimeInMillis());
        if(settedCalendar.after(calendar) || settedCalendar.equals(calendar)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Uri uri=Uri.parse("android.resource://"+context.getPackageName()+"/raw/alarm");

            builder
                    .setContentTitle(myIntent.getStringExtra("TITLE"))
                    .setContentText(myIntent.getStringExtra("TEXT"))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSound(uri);

            Intent intent = new Intent(context, MedicationController.class);
            @SuppressLint("WrongConstant") PendingIntent pi = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(pi);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());
           /* MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);

            mediaPlayer.start();
*/

        }



    }
}
