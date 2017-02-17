package com.example.pc.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.myapplication.R;
import com.example.pc.myapplication.common.XMPPUtil;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import java.util.HashMap;
import java.util.Map;


public class ResigterActivity extends Activity {

    private EditText userName,passWord,server_ip,rePassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);
        initView();
    }

    private void initView(){
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        rePassWord = (EditText) findViewById(R.id.rePassWord);
        server_ip = (EditText) findViewById(R.id.server_ip);
    }

    public void onclick_register(View view){
        if(!verify()){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                createAccount(server_ip.getText().toString(),userName.getText().toString(),passWord.getText().toString());
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(ResigterActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
        }
    };

    //注册新用户
    public void createAccount(String server,String userName,String passWord){
        Message message = new Message();
        try{
            XMPPConnection connection = XMPPUtil.getXMPPConnection(server);

            Registration reg = new Registration();
            reg.setType(IQ.Type.SET);
            reg.setTo(connection.getServiceName());
            Map<String,String> attributes = new HashMap<String,String>();
            attributes.put("username",userName);
            attributes.put("password",passWord);
            reg.setAttributes(attributes);

            PacketFilter filter  = new AndFilter(new PacketIDFilter(reg.getPacketID()),new PacketTypeFilter(IQ.class));
            PacketCollector collector = connection.createPacketCollector(filter);
            connection.sendPacket(reg);
            IQ result = (IQ) collector.nextResult(connection.getPacketReplyTimeout());
            collector.cancel();

            if(result == null){
                throw new Exception("服务器没有响应。");
            }else if(result.getType() == IQ.Type.RESULT){
                message.obj = "注册成功。";
                finish();
            }else{
                if(result.getError().toString().toLowerCase().contains("conflict")){
                    throw new Exception("该用户已存在。");
                }else{
                    throw new Exception("未知错误。");
                }
            }
        }catch (Exception e){
            message.obj = e.getMessage();
        }finally {
            handler.sendMessage(message);
        }
    }

    //校验用户信息
    private boolean verify(){
        if("".equals(userName.getText().toString().trim())){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(passWord.getText().toString().trim())){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(rePassWord.getText().toString().trim())){
            Toast.makeText(this,"请重复密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(server_ip.getText().toString().trim())){
            Toast.makeText(this,"请输入主机IP",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passWord.getText().toString().trim().equals(rePassWord.getText().toString().trim())){
            Toast.makeText(this,"请保持两次密码一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
