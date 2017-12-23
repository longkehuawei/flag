package com.longke.flag.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longke.flag.R;

import java.lang.reflect.Method;

import static android.R.attr.id;

/**
 * Created by Clevo on 2016/6/10.
 */
public class AndroidActionSheetFragment extends Fragment {

    //是否已经关闭
    boolean isDismiss=true;

    View decorView;
    //添加进入的view
    View realView;
    //添加进入的第一个view
    View pop_child_layout;
    View publishDong;
    View publishFlag;

    public static AndroidActionSheetFragment newItemInstance(String title, String[] items) {
        AndroidActionSheetFragment fragment=new AndroidActionSheetFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title", title);
        bundle.putStringArray("items", items);
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AndroidActionSheetFragment newGridInstance(String title, String[] items, int[] images) {
        AndroidActionSheetFragment fragment=new AndroidActionSheetFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title", title);
        bundle.putStringArray("items", items);
        bundle.putIntArray("images", images);
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InputMethodManager manager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            View focusView=getActivity().getCurrentFocus();
            manager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }

        realView=inflater.inflate(R.layout.view_actionsheet, container, false);
        initViews(realView);
        decorView=getActivity().getWindow().getDecorView();
        ((ViewGroup) decorView).addView(realView);
        startPlay();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViews(View view) {
        pop_child_layout=view.findViewById(R.id.pop_child_layout);
        publishDong = view.findViewById(R.id.publish_dong);
        publishFlag = view.findViewById(R.id.publish_flag);
        publishDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(1);
                    dismiss();
                }

            }
        });
        publishFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(0);
                    dismiss();
                }

            }
        });
        pop_child_layout.setVisibility(View.INVISIBLE);
        realView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        String title=getArguments().getString("title");



    }

    private void show(final FragmentManager manager, final String tag) {
        if (manager.isDestroyed() || !isDismiss) {
            return;
        }
        isDismiss=false;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.add(AndroidActionSheetFragment.this, tag);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    private void dismiss() {
        if (isDismiss) {
            return;
        }
        isDismiss=true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getChildFragmentManager().popBackStack();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.remove(AndroidActionSheetFragment.this);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlay();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) decorView).removeView(realView);
            }
        }, 500);
    }

    public static Builder build(FragmentManager fragmentManager) {
        Builder builder= new Builder(fragmentManager);
        return builder;
    }

    public static class Builder {

        FragmentManager fragmentManager;

        //默认tag，用来校验fragment是否存在
        String tag="ActionSheetFragment";
        //ActionSheet的Title
        String title="title";
        //ActionSheet上ListView或者GridLayout上相关文字、图片
        String[] items;
        int[] images;
        //ActionSheet点击后的回调
        OnItemClickListener onItemClickListener;
        //点击取消之后的回调
        OnCancelListener onCancelListener;
        //提供类型，用以区分ListView或者GridLayout
        CHOICE choice;

        public enum CHOICE {
            ITEM, GRID
        }

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager=fragmentManager;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setItems(String[] items) {
            this.items = items;
            return this;
        }

        public Builder setImages(int[] images) {
            this.images = images;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setChoice(CHOICE choice) {
            this.choice = choice;
            return this;
        }

        public void show() {
            AndroidActionSheetFragment fragment;
            if (choice==CHOICE.ITEM) {
                fragment=AndroidActionSheetFragment.newItemInstance(title, items);

                fragment.setOnItemClickListener(onItemClickListener);
                fragment.setOnCancelListener(onCancelListener);
                fragment.show(fragmentManager, tag);
            }
            if (choice==CHOICE.GRID) {
                fragment=AndroidActionSheetFragment.newGridInstance(title, items, images);
                fragment.setOnItemClickListener(onItemClickListener);
                fragment.show(fragmentManager, tag);
            }
        }

    }

    OnItemClickListener onItemClickListener;
    OnCancelListener onCancelListener;

    public interface OnCancelListener {
        void onCancelClick();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 得到屏幕高度
     * @return 单位:px
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 得到屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void startPlay() {
        pop_child_layout.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight=pop_child_layout.getMeasuredHeight();
                ValueAnimator valueAnimator=ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        pop_child_layout.setVisibility(View.VISIBLE);
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator=new ArgbEvaluator();
                        realView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#00000000"), Color.parseColor("#70000000")));
                        //当底部存在导航栏并且decorView获取的高度不包含底部状态栏的时候，需要去掉这个高度差
                        if (getNavBarHeight(pop_child_layout.getContext())>0 && decorView.getMeasuredHeight()!=getScreenHeight(pop_child_layout.getContext())) {
                            pop_child_layout.setTranslationY((moveHeight+getNavBarHeight(pop_child_layout.getContext()))*(1-animation.getAnimatedFraction())-getNavBarHeight(pop_child_layout.getContext()));
                        }
                        else {
                            pop_child_layout.setTranslationY(moveHeight*(1-animation.getAnimatedFraction()));
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }

    private void stopPlay() {
        pop_child_layout.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight=pop_child_layout.getMeasuredHeight();
                ValueAnimator valueAnimator=ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator=new ArgbEvaluator();
                        realView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#70000000"), Color.parseColor("#00000000")));
                        if (getNavBarHeight(pop_child_layout.getContext())>0 && decorView.getMeasuredHeight()!=getScreenHeight(pop_child_layout.getContext())) {
                            pop_child_layout.setTranslationY((moveHeight+getNavBarHeight(pop_child_layout.getContext()))*animation.getAnimatedFraction()-getNavBarHeight(pop_child_layout.getContext()));
                        }
                        else {
                            pop_child_layout.setTranslationY(moveHeight*animation.getAnimatedFraction());
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }

    /**
     * 获取底部导航栏高度
     * @param context
     * @return
     */
    public static int getNavBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }

        return navigationBarHeight;
    }

    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }
}
