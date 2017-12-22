package com.longke.flag.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.longke.flag.R;
import com.longke.flag.common.SimpleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DetailsFlagActivity extends AppCompatActivity {



    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @InjectView(R.id.appbar)
    AppBarLayout mAppbar;

    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.redirect)
    TextView mRedirect;
    @InjectView(R.id.bottombar_retweet)
    LinearLayout mBottombarRetweet;
    @InjectView(R.id.comment)
    TextView mComment;
    @InjectView(R.id.bottombar_comment)
    LinearLayout mBottombarComment;
    @InjectView(R.id.feedlike)
    TextView mFeedlike;
    @InjectView(R.id.bottombar_attitude)
    LinearLayout mBottombarAttitude;
    @InjectView(R.id.bottombar_layout)
    LinearLayout mBottombarLayout;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_immersive_3);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SystemBarHelper.immersiveStatusBar(this, 0);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight();
                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight() * 2;
               // mNickname.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            }
        });


        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SimpleFragment.newInstance("关联动态"), "关联动态");
        adapter.addFragment(SimpleFragment.newInstance("评论"), "评论");

        mViewpager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewpager);

    }


    class SimpleViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SimpleViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
