package com.capston.googolhkl.scheduler;

/**
 * Created by googolhkl on 2016. 11. 6..
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import com.capston.googolhkl.schduler.R;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.OpenChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import com.google.firebase.iid.FirebaseInstanceId;


public class testActivity extends Activity{

    private static final String appId = "9FBD1E10-6A0A-4D2D-B8ED-79164DA35F4D";
    public static String sUserId="default";
    private String mNickname;
    private String key;
    private String value;
    private String title;
    private int i=0;
    static private int count =0;
    private int channelMaxSize=0;
    private SendBirdOpenChannelListActivity.SendBirdChannelListFragment mSendBirdChannelListFragment;
    private enum State {DISCONNECTED, CONNECTING, CONNECTED}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // MAC 주소 얻기
        WifiManager mng = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo info = mng.getConnectionInfo();
        String mac = info.getMacAddress();

        // 핸드폰 번호 얻기
        TelephonyManager phone = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNum = phone.getLine1Number();
        sUserId = phoneNum;


        // 채팅을 위한 초기화
        SendBird.init(appId, this);

        //과목 정보 얻기
        Intent intent = getIntent();
        ClassInformation ci = (ClassInformation)intent.getSerializableExtra("data");


        // 채팅방의 key와 value
        key = ci.getSchoolName() +":"+ ci.getClassNumber();
        value = ci.getClassName();

        // 채팅방의 이름
        title = ci.getSchoolName() +" " + ci.getClassName();
        //Toast.makeText(getApplicationContext(),"mac = " + mac ,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"phone = " + phoneNum ,Toast.LENGTH_LONG).show();


        findViewById(R.id.testBox).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                View view = testActivity.this.getLayoutInflater().inflate(R.layout.scheduler_nickname, null);
                final EditText chName = (EditText) view.findViewById(R.id.nickname);

                AlertDialog.Builder builder = new AlertDialog.Builder(testActivity.this);
                builder.setTitle("오픈채팅 입장")
                        .setMessage("채팅방에서 사용할 닉네임을 입력해주세요")
                        .setCancelable(false)
                        .setView(view)
                        .setPositiveButton("입장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                count =0;

                                mNickname = chName.getText().toString();

                                connect();
                                Helper.hideKeyboard(testActivity.this);

                                //Intent intent = new Intent(testActivity.this, SendBirdOpenChannelListActivity.class);
                                //startActivity(intent);
                                //finish();

                                OpenChannelListQuery mChannelListQuery = OpenChannel.createOpenChannelListQuery();
                                mChannelListQuery.next(new OpenChannelListQuery.OpenChannelListQueryResultHandler() {
                                    @Override
                                    public void onResult(final List<OpenChannel> channels, SendBirdException e) {
                                        if (e != null) {
                                            Toast.makeText(testActivity.this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        final List<String> keys = new ArrayList<String>();
                                        keys.add(key);
                                        channelMaxSize = channels.size();
                                        for( i=0; i<channels.size(); i++) {

                                            final OpenChannel channel = channels.get(i);
                                            final int j = i;
                                            //channel = channels.get(i);
                                            channels.get(i).getMetaData(keys, new BaseChannel.MetaDataHandler() {
                                                @Override
                                                public void onResult(Map<String, String> map, SendBirdException e) {
                                                    if (e != null) {
                                                        return;
                                                    }

                                                    String str =  map.get(key);
                                                    // 방이 있을 때 바로 입장
                                                    if(value.equals(str)) {
                                                        Intent intent = new Intent(testActivity.this, SendBirdOpenChatActivity.class);
                                                        intent.putExtra("channel_url", channel.getUrl());
                                                        startActivity(intent);
                                                    }
                                                    // 방이 없을 때 생성하고 입장
                                                    else{
                                                        count++;

                                                        if(count==channelMaxSize){
                                                            Toast.makeText(testActivity.this, "방이없다=" + count, Toast.LENGTH_SHORT).show();
                                                            List<User> operators = new ArrayList<>();
                                                            operators.add(SendBird.getCurrentUser());

                                                            OpenChannel.createChannel(title, null, null, operators, new OpenChannel.OpenChannelCreateHandler() {
                                                                @Override
                                                                public void onResult(OpenChannel openChannel, SendBirdException e) {
                                                                    if (e != null) {
                                                                        Toast.makeText(testActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        return;
                                                                    }
                                                                    HashMap<String, String> data = new HashMap<String, String>();
                                                                    data.put(key,value);
                                                                    openChannel.createMetaData(data, new BaseChannel.MetaDataHandler() {
                                                                        @Override
                                                                        public void onResult(Map<String, String> map, SendBirdException e) {
                                                                            if(e != null){
                                                                                return;
                                                                            }

                                                                        }
                                                                    });

                                                                    /*
                                                                    if (!mSendBirdChannelListFragment.mChannelListQuery.hasNext()) {
                                                                        mSendBirdChannelListFragment.mAdapter.add(openChannel);
                                                                    }*/



                                                                    Intent intent = new Intent(testActivity.this, SendBirdOpenChatActivity.class);
                                                                    intent.putExtra("channel_url", openChannel.getUrl());
                                                                    startActivity(intent);
                                                                }
                                                            });
                                                            }
                                                    }
                                                }
                                            });
                                        }
                                    }

                                });

                            }

                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        }
        );

    }

    private void connect() {
        SendBird.connect(sUserId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {

                if (e != null) {
                    //Toast.makeText(testActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //setState(State.DISCONNECTED);
                    return;
                }

                String nickname = mNickname;

                SendBird.updateCurrentUserInfo(nickname, null, new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {
                            //Toast.makeText(testActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                           // setState(State.DISCONNECTED);
                            return;
                        }

                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("user_id", sUserId);
                        editor.putString("nickname", mNickname);
                        editor.commit();
                        //Toast.makeText(testActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //setState(State.CONNECTED);
                    }
                });

                if (FirebaseInstanceId.getInstance().getToken() == null) return;

                //Toast.makeText(testActivity.this, FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();


                SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(), new SendBird.RegisterPushTokenWithStatusHandler() {
                    @Override
                    public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                        if (e != null) {
                            //Toast.makeText(testActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

            }
        });

        //setState(State.CONNECTING);
    }

}