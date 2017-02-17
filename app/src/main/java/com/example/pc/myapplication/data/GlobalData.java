package com.example.pc.myapplication.data;

import android.app.Application;

import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by pc on 2016/12/16.
 */

public class GlobalData extends Application{
    public XMPPConnection xmppConnection;
    public LoginData loginData = new LoginData();
}
