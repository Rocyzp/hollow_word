package com.example.pc.myapplication.data;

import android.content.Context;

import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by pc on 2016/12/16.
 */

public class DataWareHouse {
    public static GlobalData getGlobalData(Context context){
        return (GlobalData)context.getApplicationContext();
    }

    public static XMPPConnection getXMPPConnection(Context context){
        return getGlobalData(context).xmppConnection;
    }

    public static void setXMPPConnection(Context context,XMPPConnection xmppConnection){
        getGlobalData(context).xmppConnection = xmppConnection;
    }
}
