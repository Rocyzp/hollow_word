package com.example.pc.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.pc.myapplication.data.DataWareHouse;

import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by pc on 2016/12/21.
 */

public class BaseActivity extends Activity {
    protected XMPPConnection xmppConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xmppConnection = DataWareHouse.getXMPPConnection(this);
    }
}
