package com.sigran0.sendreceive.interfaces;

import com.sigran0.sendreceive.managers.ModelManager;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018-06-02.
 */

public interface RetrofitItemStateChange {

    public static final String API_URL = "hj";
    @POST("fcm/send")
    List<ModelManager.ItemData> notifyItemStateChange();
}
