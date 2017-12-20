package com.longke.flag.view;


import com.longke.flag.entity.NavBean;

/**
 * Created by qizhenghao on 17/3/7.
 */
public abstract class AbstractSubject<T>{

    public abstract void attachObserver(T observer);

    public abstract void detachObserver(T observer);

    public abstract void initTabData(NavBean[] navs);

    public abstract void refreshTabData(NavBean nav);

    public abstract void setSelectedType(@NavBean.TYPE int type);

    public abstract void setSelectedPosition(int position);
}
