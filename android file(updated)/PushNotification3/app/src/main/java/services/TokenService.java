package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by amir on 8/30/16.
 */
public class TokenService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences preferences=getSharedPreferences("token_file",MODE_PRIVATE);
        preferences.edit().putString("token",token).commit();


    }




}
