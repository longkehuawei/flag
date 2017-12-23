package com.longke.flag.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.longke.flag.R;
import com.longke.flag.activity.PublishFlagActivity;
import com.longke.flag.adapter.TabFragmentPagerAdapter;
import com.longke.flag.util.ToastUtil;
import com.longke.flag.view.AndroidActionSheetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @InjectView(R.id.myViewPager)
    ViewPager mMyViewPager;
    List<Fragment> list;
    @InjectView(R.id.iv_add)
    ImageView mIvAdd;
    @InjectView(R.id.follow_tv)
    TextView mFollowTv;
    @InjectView(R.id.hot_tv)
    TextView mHotTv;
    @InjectView(R.id.title_bar)
    RelativeLayout mTitleBar;
    private Context mContext;
    private PopupWindow mPopWindow;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        /*FollowAdapter followAdapter = new FollowAdapter();
        mList.setAdapter(followAdapter);*/
        list = new ArrayList<>();
        Fragment followFragment = new FollowFragment();
        Fragment followFragment1 = new FollowFragment();
       // HotFragment hotFragment = new HotFragment();
        list.add(followFragment);
        list.add(followFragment1);
        FragmentManager fm = getChildFragmentManager();
        //初始化自定义适配器
        TabFragmentPagerAdapter mAdapter = new TabFragmentPagerAdapter(fm, list);
        //绑定自定义适配器
        mMyViewPager.setAdapter(mAdapter);
        mMyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Drawable drawable = getResources().getDrawable(R.drawable.juxing);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    Drawable xiala = getResources().getDrawable(R.drawable.xiala);
                    xiala.setBounds(0, 0, xiala.getMinimumWidth(), xiala.getMinimumHeight());
                    mFollowTv.setCompoundDrawables(null, null, xiala, drawable);
                    mHotTv.setCompoundDrawables(null, null, null, null);

                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.juxing);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    Drawable xiala = getResources().getDrawable(R.drawable.xiala);
                    xiala.setBounds(0, 0, xiala.getMinimumWidth(), xiala.getMinimumHeight());
                    mHotTv.setCompoundDrawables(null, null, null, drawable);
                    mFollowTv.setCompoundDrawables(null, null, xiala, null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SystemBarHelper.tintStatusBar(getActivity(), getResources().getColor(R.color.white));
    }
    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp =  getActivity().getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getActivity().getWindow().setAttributes(lp);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getActivity().getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().getWindow().setAttributes(attr);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_add, R.id.follow_tv, R.id.hot_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                AndroidActionSheetFragment.build(getChildFragmentManager()).setChoice(AndroidActionSheetFragment.Builder.CHOICE.ITEM).setTitle("标题").setTag("MainActivity")
                        .setItems(new String[]{"1", "2", "3", "4", "5", "6"}).setOnItemClickListener(new AndroidActionSheetFragment.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if(position==0){
                            startActivity(new Intent(getActivity(),PublishFlagActivity.class));
                        }else{

                        }
                    }
                }).show();
                break;
            case R.id.follow_tv:
                showPopupWindow();
                break;
            case R.id.hot_tv:
                mMyViewPager.setCurrentItem(1);
                break;
        }
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popuplayout, null);
        View guanzhu_tv = contentView.findViewById(R.id.guanzhu_tv);
        View guanzhu_flag = contentView.findViewById(R.id.guanzhu_flag);
        guanzhu_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        guanzhu_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        mPopWindow.showAsDropDown(mTitleBar);

    }
}
