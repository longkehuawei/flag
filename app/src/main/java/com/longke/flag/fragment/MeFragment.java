package com.longke.flag.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.systembar.SystemBarHelper;
import com.longke.flag.R;
import com.longke.flag.adapter.InfoAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private AppCompatActivity mAppCompatActivity;
    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mAppCompatActivity= (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_me2, container, false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)view. findViewById(R.id.collapsing_toolbar);
        //设置CollapsingToolbarLayout的标题文字
        collapsingToolbar.setTitle(" ");
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBackPressed();
            }
        });

       SystemBarHelper.immersiveStatusBar(mAppCompatActivity, 0);
        SystemBarHelper.setHeightAndPadding(mAppCompatActivity,toolbar);
        //设置ViewPager
        ViewPager viewPager = (ViewPager) view. findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //设置tablayout，viewpager上的标题
        TabLayout tabLayout = (TabLayout) view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ea6a6a"));
        return view;
    }
    /**
     * 设置item
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        InfoAdapter adapter = new InfoAdapter(getChildFragmentManager());
        adapter.addFragment(new MessageFragment(), "他的动态");
        adapter.addFragment(new FollowFragment(), "他的flag");
        viewPager.setAdapter(adapter);
    }

}
