package com.sigran0.sendreceive.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Administrator on 2018-06-02.
 */

public class MessagingService extends FirebaseMessagingService {

    public static final String TAG = "fucking";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
    }
}
