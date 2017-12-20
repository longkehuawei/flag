package com.longke.flag.http;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.Gson;
import com.longke.flag.entity.Data;
import com.longke.flag.event.MessageEvent;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by longke on 2017/12/13.
 */
public class HttpUtil {
    private static final String TAG ="Http" ;
    private MyOkHttp mMyOkhttp;
    private static HttpUtil ourInstance = new HttpUtil();

    public static HttpUtil getInstance() {
        return ourInstance;
    }

    private HttpUtil() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        mMyOkhttp = new MyOkHttp(okHttpClient);
    }
    public MyOkHttp getOkHttp(){

        return mMyOkhttp;
    }
    public String GetTimestamp(final String tag) {
        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetTimestamp)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        Log.d(TAG, "doPost onSuccess JSONObject:" + response);
                        Data data= new Gson().fromJson(response.toString(), Data.class);
                        EventBus.getDefault().post(new MessageEvent(tag,data.getData().getTimestamp()+""));
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        Log.d(TAG, "doPost onSuccess JSONArray:" + response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.d(TAG, "doPost onFailure:" + error_msg);
                    }
                });
        return null;
    }



}
