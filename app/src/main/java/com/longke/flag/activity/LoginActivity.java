package com.longke.flag.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.event.MessageEvent;
import com.longke.flag.http.HttpUtil;
import com.longke.flag.http.Urls;
import com.longke.flag.util.ToastUtil;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @InjectView(R.id.et_quick_phone)
    EditText mEtQuickPhone;
    @InjectView(R.id.et_pwd)
    EditText mEtPwd;
    @InjectView(R.id.ll_quick_login)
    LinearLayout mLlQuickLogin;
    @InjectView(R.id.login_btn)
    Button mLoginBtn;
    @InjectView(R.id.forget_pwd)
    TextView mForgetPwd;
    @InjectView(R.id.register)
    TextView mRegister;
    @InjectView(R.id.weibo)
    TextView mWeibo;
    @InjectView(R.id.weixin)
    TextView mWeixin;
    @InjectView(R.id.qq)
    TextView mQq;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar
        setContentView(R.layout.activity_login_view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("Login".equals(messageEvent.getTag())) {
            Login(messageEvent.getMessage());
        }
    }

    @OnClick({R.id.login_btn, R.id.forget_pwd, R.id.register, R.id.weibo, R.id.weixin, R.id.qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if(TextUtils.isEmpty(mEtQuickPhone.getText().toString())){
                    ToastUtil.showShort(LoginActivity.this,"请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(mEtPwd.getText().toString())){
                    ToastUtil.showShort(LoginActivity.this,"请输入密码");
                    return;
                }
                if(mEtQuickPhone.getText().toString().length()<11){
                    ToastUtil.showShort(LoginActivity.this,"请输入有效的手机号码");
                    return;
                }
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("登陆中,请稍候...");

                dialog.show();
                HttpUtil.getInstance().GetTimestamp("Login");
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("from",1));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("from",0));
                break;
            case R.id.weibo:
                break;
            case R.id.weixin:
                break;
            case R.id.qq:
                break;
        }
    }
    public void Login(String timestamp){

        HttpUtil.getInstance().getOkHttp().get().addHeader("X_MACHINE_ID", "ED5E3E2585B2477ABCA664EAAF32DC2A").addHeader("X_REG_SECRET", "er308343cf381bd4a37a185654035475d4c67842").url(Urls.Login)
                .addParam("Timestamp", timestamp)
                .addParam("userCode", mEtQuickPhone.getText().toString())
                .addParam("pwd", mEtPwd.getText().toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        try {
                            if(response.getBoolean("Success")){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                ToastUtil.showShort(LoginActivity.this,response.getString("Message"));
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        dialog.dismiss();
                    }
                });

    }
}
