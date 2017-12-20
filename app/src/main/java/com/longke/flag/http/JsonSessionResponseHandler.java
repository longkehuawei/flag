package com.longke.flag.http;

import android.util.Log;

import com.longke.flag.util.SharedPreferencesUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.IResponseHandler;
import com.tsy.sdk.myokhttp.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by longke on 2017/12/18.
 */

public class JsonSessionResponseHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";
        //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
        Headers headers = response.headers();
        Log.d("info_headers", "header " + headers);
        List<String> cookies = headers.values("Set-Cookie");
        String session = cookies.get(0);
        Log.d("info_cookies", "onResponse-size: " + cookies);

        final String  s = session.substring(0, session.indexOf(";"));


        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail read response body");

            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;

        try {
            final Object result = new JSONTokener(finalResponseBodyStr).nextValue();
            if(result instanceof JSONObject) {
                MyOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(response.code(), (JSONObject) result,s);
                    }
                });
            } else if(result instanceof JSONArray) {
                MyOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(response.code(), (JSONArray) result);
                    }
                });
            } else {
                LogUtils.e("onResponse fail parse jsonobject, body=" + finalResponseBodyStr);
                MyOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(response.code(), "fail parse jsonobject, body=" + finalResponseBodyStr);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail parse jsonobject, body=" + finalResponseBodyStr);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail parse jsonobject, body=" + finalResponseBodyStr);
                }
            });
        }
    }

    @Override
    public void onFailure(int statusCode, String error_msg) {

    }

    public void onSuccess(int statusCode, JSONObject response,String session) {
        LogUtils.w("onSuccess(int statusCode, JSONObject response) was not overriden, but callback was received");
    }

    public void onSuccess(int statusCode, JSONArray response) {
        LogUtils.w("onSuccess(int statusCode, JSONArray response) was not overriden, but callback was received");
    }

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
