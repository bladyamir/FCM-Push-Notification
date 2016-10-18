package services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import test.amir.pushnotification3.MainActivity;
import test.amir.pushnotification3.R;

/**
 * Created by amir on 9/4/16.
 */
public class NotificationService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message=remoteMessage.getNotification().getBody();
        String title=remoteMessage.getNotification().getTitle();

        showNotification(message,title);
    }

    private void showNotification(String message,String title) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(this);
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
      nBuilder.setContentTitle(title);
        nBuilder.setContentText(message);
        nBuilder.setAutoCancel(true);
        nBuilder.setContentIntent(pendingIntent);

        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,nBuilder.build());



    }
}
