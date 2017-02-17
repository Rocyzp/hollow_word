package com.example.pc.myapplication.common;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * 连接XMPP服务器
 *
 * Created by pc on 2016/12/16.
 */

public class XMPPUtil {
    public static XMPPConnection getXMPPConnection(String server,int port){
        try{
            ConnectionConfiguration configuration = new ConnectionConfiguration(server,port);
            configuration.setReconnectionAllowed(true);//如果没连上，允许重复连接
            configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);//安全模式
            configuration.setSendPresence(true);

            SASLAuthentication.supportSASLMechanism("PLAIN",0);

            XMPPConnection xmppConnection = new XMPPTCPConnection(configuration,null);
            xmppConnection.connect();
            return xmppConnection;

        }catch (Exception e){

        }
        return null;
    }

    public static XMPPConnection getXMPPConnection(String server){
        return getXMPPConnection(server,5222);
    }
}
