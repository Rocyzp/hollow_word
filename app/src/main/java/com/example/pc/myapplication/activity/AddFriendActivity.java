package com.example.pc.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.myapplication.R;

public class AddFriendActivity extends BaseActivity {

    private EditText editText_name,editText_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    private void initView(){
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_note = (EditText) findViewById(R.id.editText_note);
    }

    public void onClick_add_friends(View view){
        String account = editText_name.getText().toString().trim();
        String alias = editText_note.getText().toString().trim();

        if("".equals(account)){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if("".equals(alias)){
            alias = account;
        }

        try{
            xmppConnection.getRoster().createEntry(account,alias,null);
            Intent intent = new Intent();
            intent.putExtra("account",account);
            intent.putExtra("alias",alias);
            setResult(1,intent);
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"添加失败"+":"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}
