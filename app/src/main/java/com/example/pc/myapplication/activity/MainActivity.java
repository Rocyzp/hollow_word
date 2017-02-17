package com.example.pc.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pc.myapplication.R;
import com.example.pc.myapplication.adapter.FriendListAdapter;
import com.example.pc.myapplication.data.UserData;
import com.example.pc.myapplication.view.SlideMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_back,add_friend_tv;
    private SlideMenu slideMenu;
    private ListView friend_lv;
    private FriendListAdapter friendListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initView(){
        tv_back = (TextView) findViewById(R.id.tv_back);
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        add_friend_tv = (TextView) findViewById(R.id.add_friend_tv);
        friend_lv = (ListView) findViewById(R.id.friend_lv);

        friendListAdapter = new FriendListAdapter(this,xmppConnection.getRoster().getEntries());
        friend_lv.setAdapter(friendListAdapter);
    }

    private void initListener(){
        tv_back.setOnClickListener(this);
        add_friend_tv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.tv_back){
            slideMenu.switchMenu();
        }else if(id == R.id.add_friend_tv){
            Intent intent = new Intent(this,AddFriendActivity.class);
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(data !=null && requestCode == 1){
                    String name = data.getStringExtra("name");
                    String user = data.getStringExtra("user");

                    UserData userData = new UserData(name,user);
                    friendListAdapter.addUserData(userData);
                }
        }
    }
}
