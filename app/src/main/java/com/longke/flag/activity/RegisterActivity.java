package com.longke.flag.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longke.flag.R;
import com.longke.flag.entity.ImageCodeRes;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.JsonSessionResponseHandler;
import com.longke.flag.http.Urls;
import com.longke.flag.util.CodeUtils;
import com.longke.flag.util.ToastUtil;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.apache.commons.codec.binary.Base64;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;



public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    @InjectView(R.id.iv_back)
    ImageView mIvBack;
    @InjectView(R.id.addrss_name_tv)
    TextView mAddrssNameTv;
    @InjectView(R.id.title_bar)
    RelativeLayout mTitleBar;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.tu_code)
    EditText mTuCode;
    @InjectView(R.id.et_check_code)
    EditText mEtCheckCode;
    @InjectView(R.id.bt_get_sms_code)
    Button mBtGetSmsCode;
    @InjectView(R.id.et_pwd)
    EditText mEtPwd;
    @InjectView(R.id.ll_quick_login)
    LinearLayout mLlQuickLogin;
    @InjectView(R.id.bt_get_check_code)
    Button mBtGetCheckCode;
    @InjectView(R.id.img_code)
    ImageView mImgCode;
    String sessionId;
    ImageCodeRes data;
    int from;
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar
        setContentView(R.layout.activity_register_view);
        ButterKnife.inject(this);
        from=getIntent().getIntExtra("from",0);
        if(from==1){
            mAddrssNameTv.setText("找回密码");
            mBtGetCheckCode.setText("修改");
        }
        HttpUtil.getInstance().GetTimestamp("getimagecode");

        Log.d(TAG, "dtimestamp" + System.currentTimeMillis());

        //HttpUtil.getInstance().getOkHttp().get().url("http://www.baidu.com").tag(this).enqueue();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("getimagecode".equals(messageEvent.getTag())) {
            HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.GetImgValidCode)
                    .addParam("timestamp", messageEvent.getMessage())
                    .tag(this)
                    .enqueue(new JsonSessionResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response,String session) {
                            sessionId=session;
                            data = new Gson().fromJson(response.toString(), ImageCodeRes.class);
                            if(!data.isSuccess()){
                                ToastUtil.showShort(RegisterActivity.this,data.getMessage());
                            }
                            //sessionId=data.getData().getSessionId();
                            CodeUtils codeUtils = CodeUtils.getInstance();
                            Bitmap bitmap = codeUtils.createBitmap(data.getData().getValidCode());
                            mImgCode.setImageBitmap(bitmap);
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
        }else if("getphonecode".equals(messageEvent.getTag())){
            byte[] encodeBase64 = new byte[0];
            try {
                encodeBase64 = Base64.encodeBase64(mTuCode.getText().toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A")
                    .addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").addHeader("cookie",sessionId)
                    .url(Urls.GetPhoneValidCode)
                        .addParam("timestamp", messageEvent.getMessage())
                        .addParam("mobile", mEtPhone.getText().toString())
                        .addParam("failureSecond", "60000")
                        .addParam("imgValidCode",new String(encodeBase64) )
                        .addParam("checkHasRegist",(from==1)?"true":"false" )
                        .addParam("type",(from==1)?"2":"1" )
                        .tag(this)
                        .enqueue(new JsonResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, JSONObject response) {
                                Log.d(TAG, "doPost onFailure:" + response);
                                try {
                                    if(response.getBoolean("Success")){

                                    }else{
                                        ToastUtil.showShort(RegisterActivity.this,response.getString("Message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                               /* ImageCodeRes data = new Gson().fromJson(response.toString(), ImageCodeRes.class);
                                CodeUtils codeUtils = CodeUtils.getInstance();
                                Bitmap bitmap = codeUtils.createBitmap(data.getData().getValidCode());
                                mImgCode.setImageBitmap(bitmap);*/
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
        }else if("Register".equals(messageEvent.getTag())){
            register(messageEvent.getMessage());
        }

    }
  public void register(String timestamp){
      byte[] encodeBase64 = new byte[0];
      try {
          encodeBase64 = Base64.encodeBase64(mTuCode.getText().toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      HttpUtil.getInstance().getOkHttp().post().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A")
              .addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").addHeader("cookie",sessionId)
              .url((from==1)?Urls.ForgetPwd:Urls.Register)
              .addParam("Timestamp", timestamp)
              .addParam("Mobile", mEtPhone.getText().toString())
              .addParam("Pwd", mEtPwd.getText().toString())
              .addParam("ImgValidCode",new String(encodeBase64))
              .addParam("PhoneValidCode",mEtCheckCode.getText().toString() )
              .tag(this)
              .enqueue(new JsonResponseHandler() {
                  @Override
                  public void onSuccess(int statusCode, JSONObject response) {
                      try {
                          if(response.getBoolean("Success")){
                                if(from==1){
                                    ToastUtil.showShort(RegisterActivity.this,"设置密码成功");
                                }else{
                                    ToastUtil.showShort(RegisterActivity.this,"注册成功");
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }

                                finish();
                          }else{
                              ToastUtil.showShort(RegisterActivity.this,response.getString("Message"));
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
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

  }



    @OnClick({R.id.iv_back, R.id.bt_get_sms_code, R.id.bt_get_check_code,R.id.img_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_code:
                HttpUtil.getInstance().GetTimestamp("getimagecode");
                break;
            case R.id.bt_get_sms_code:
                myCountDownTimer.start();
                HttpUtil.getInstance().GetTimestamp("getphonecode");
                break;
            case R.id.bt_get_check_code:
                if(TextUtils.isEmpty(mEtPhone.getText().toString())){
                    ToastUtil.showShort(RegisterActivity.this,"请输入手机号码");
                    return;
                }
                if(mEtPhone.getText().toString().length()<11){
                    ToastUtil.showShort(RegisterActivity.this,"请输入有效的手机号码");
                    return;
                }
                if(TextUtils.isEmpty(mTuCode.getText().toString())){
                    ToastUtil.showShort(RegisterActivity.this,"请输入图形验证码");
                    return;
                }
                if(TextUtils.isEmpty(mEtCheckCode.getText().toString())){
                    ToastUtil.showShort(RegisterActivity.this,"请输入手机验证码");
                    return;
                }
                if(TextUtils.isEmpty(mEtPwd.getText().toString())){
                    ToastUtil.showShort(RegisterActivity.this,"请输入密码");
                    return;
                }

                HttpUtil.getInstance().GetTimestamp("Register");
                //startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                break;
        }
    }
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            mBtGetSmsCode.setClickable(false);
            mBtGetSmsCode.setText(l/1000+"s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            mBtGetSmsCode.setText("重新获取验证码");
            //设置可点击
            mBtGetSmsCode.setClickable(true);
        }
    }
}
