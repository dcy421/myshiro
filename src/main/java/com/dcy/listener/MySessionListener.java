package com.dcy.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Created by Administrator on 2017/9/1.
 */
public class MySessionListener implements SessionListener {

    public void onStart(Session session) {
        System.out.println("会话创建：" + session.getId());
    }

    public void onStop(Session session) {
        System.out.println("会话过期：" + session.getId());
    }

    public void onExpiration(Session session) {
        System.out.println("会话停止：" + session.getId());
    }
}
