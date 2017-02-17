package com.example.pc.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.myapplication.R;
import com.example.pc.myapplication.common.Const;
import com.example.pc.myapplication.common.Storage;
import com.example.pc.myapplication.common.XMPPUtil;
import com.example.pc.myapplication.data.DataWareHouse;
import com.example.pc.myapplication.data.LoginData;

import org.jivesoftware.smack.XMPPConnection;


public class LonginActivity extends Activity implements View.OnClickListener,Const{

    private Button login,registion;
    private EditText userName,passWord,server_ip;
    private CheckBox remenber_psd,slf_longin;
    private LoginData loginData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longin);
        initView();
        initUI();
    }

    private void initView(){
        login = (Button) findViewById(R.id.login);
        registion = (Button) findViewById(R.id.registion);
        login.setOnClickListener(this);
        registion.setOnClickListener(this);

        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        server_ip = (EditText) findViewById(R.id.server_ip);

        remenber_psd = (CheckBox) findViewById(R.id.remenber_psd);
        slf_longin = (CheckBox) findViewById(R.id.slf_longin);

        loginData = DataWareHouse.getGlobalData(this).loginData;

    }

    private void initUI(){
        //存到本地
        loginData.userName = Storage.getString(this,KEY_USERNAME,loginData.userName);
        loginData.passWord = Storage.getString(this,KEY_PASSWORD,loginData.passWord);
        loginData.server = Storage.getString(this,KEY_LOGIN_SERVER,loginData.server);

        loginData.isAutoLogin = Storage.getBoolean(this,KEY_AUTO_LOGIN,loginData.isAutoLogin);
        loginData.isSavePsd = Storage.getBoolean(this,KEY_SAVE_PASSWORD,loginData.isSavePsd);

        userName.setText(loginData.userName);
        server_ip.setText(loginData.server);

        remenber_psd.setChecked(loginData.isSavePsd);
        slf_longin.setChecked(loginData.isAutoLogin);

        if(loginData.isSavePsd){
            passWord.setText(loginData.passWord);
        }

        if(loginData.isAutoLogin){
//            onClick(login);
        }
    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        if(v==R.id.login){
            saveLoginDate();
        }else if(v==R.id.registion){
            Intent intent = new Intent(this,ResigterActivity.class);
            startActivity(intent);
        }
    }

    private Handler handler =new Handler();

    private void saveLoginDate() {
        //存到全局
        loginData.userName = userName.getText().toString();
        loginData.passWord = passWord.getText().toString();
        loginData.server = server_ip.getText().toString();

        loginData.isAutoLogin = slf_longin.isChecked();
        loginData.isSavePsd = remenber_psd.isChecked();

        //存到本地
        Storage.putString(this,KEY_USERNAME,loginData.userName);
        Storage.putString(this,KEY_PASSWORD,loginData.passWord);
        Storage.putString(this,KEY_LOGIN_SERVER,loginData.server);

        Storage.putBoolean(this,KEY_AUTO_LOGIN,loginData.isAutoLogin);
        Storage.putBoolean(this,KEY_SAVE_PASSWORD,loginData.isSavePsd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (login()) {
                    Intent intent = new Intent(LonginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LonginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    private boolean login(){
        try{
            XMPPConnection xmppConnection = XMPPUtil.getXMPPConnection(loginData.server);
            if(xmppConnection == null){
                throw new Exception("服务器失败");
            }
            xmppConnection.login(loginData.userName,loginData.passWord);
            DataWareHouse.setXMPPConnection(this,xmppConnection);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
