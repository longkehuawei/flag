package com.longke.flag.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longke.flag.R;
import com.longke.flag.view.FlowTagLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends Activity {

    @InjectView(R.id.searchView)
    EditText mSearchView;
    @InjectView(R.id.cancel_btn)
    TextView mCancelBtn;

    LinearLayout mActivitySearch;
    @InjectView(R.id.hostory_tag_layout)
    FlowTagLayout mHostoryTagLayout;
    @InjectView(R.id.hot_tag_layout)
    FlowTagLayout mHotTagLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        mHostoryTagLayout.setTags(new String[]{"Tag1", "Tag2", "Tag3"});mHotTagLayout.setTags(new String[]{"Tag1", "Tag2", "Tag3"});

    }
}
