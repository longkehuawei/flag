package com.longke.flag.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.longke.flag.R;
import com.longke.flag.entity.Config;
import com.longke.flag.fragment.FindFragment;
import com.longke.flag.fragment.HomeFragment;
import com.longke.flag.fragment.MeFragment;
import com.longke.flag.fragment.MessageFragment;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    String[] title = new String[]{"首页", "周边", "我的", "更多"};
    @InjectView(R.id.tabHost)
    FragmentTabHost mTabHost;
    private Class[] fragments = new Class[]{
            HomeFragment.class,
            MessageFragment.class,
            FindFragment.class,
            MeFragment.class};
    private int[] imgRes = new int[]{
            R.drawable.ic_tab_artists_selector,
            R.drawable.ic_tab_albums_selector,
            R.drawable.ic_tab_songs_selector,
            R.drawable.ic_tab_playlists_selector
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        initFragmentTabhost();
    }


    private void initFragmentTabhost() {
        //初始化
        mTabHost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < title.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tab_item, null);
            ImageView ivTab = (ImageView) inflate.findViewById(R.id.iv_tab);
            //TextView tvTab = (TextView) inflate.findViewById(R.id.tv_tab);
            ivTab.setImageResource(imgRes[i]);
            //tvTab.setText(title[i]);
            mTabHost.addTab(mTabHost.newTabSpec(Config.title[i]).setIndicator(inflate), fragments[i], null);
        }
    }
    /**
     * 监听返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            showExitDialog();
        }
        return false;
    }
    /**
     * 显示退出窗口
     */
    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("确定要退出？")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();

        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }
}
