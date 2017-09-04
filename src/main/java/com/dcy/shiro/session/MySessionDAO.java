package com.dcy.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

/**
 * 系统安全认证实现类
 * Created by Administrator on 2017/9/1.
 */
public class MySessionDAO extends CachingSessionDAO {


    /**
     * 创建session
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return session.getId();
    }


    /**
     * 修改session
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {

    }

    /**
     * 删除
     * @param session
     */
    @Override
    protected void doDelete(Session session) {

    }


    /**
     * 读session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }


}
