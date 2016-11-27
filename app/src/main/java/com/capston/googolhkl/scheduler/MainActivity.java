package com.capston.googolhkl.scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import com.capston.googolhkl.schduler.R;

// 처음켰을 때
public class MainActivity extends AppCompatActivity {
    private ArrayList<ClassInformation> ci = new ArrayList<ClassInformation>();
    private SQLiteHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainStart();

    }

    @Override
    public boolean  onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        int id = item.getItemId();

        switch (id){

            case R.id.main_menu:
                break;

            case R.id.addSubject:
                Intent intentSubActivity1 =new Intent(MainActivity.this, AddMainActivity.class);
                intentSubActivity1.putExtra("data",ci);
                startActivity(intentSubActivity1);
                break;

            case R.id.searchSubject:
                Toast.makeText(this,"서비스 준비 중 입니다",Toast.LENGTH_LONG).show();
                break;

            case R.id.maker:
                Intent intentSubActivity3 =new Intent(MainActivity.this, MakerActivity.class);
                startActivity(intentSubActivity3);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_main);
        mainStart();
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    public void mainStart(){
        ci.clear();
        // 데이터베이스에 있는 과목 전부 받아오기
        dbHelper = new SQLiteHelper(getApplicationContext(), "classInfo.db", null,1);
        ArrayList<String> result = dbHelper.getResult();

        for(int i=0; i< result.size(); i++){
            ci.add(new ClassInformation(result.get(i)));
        }

        /* 과목 시간표에 뿌리기  */
        for(int i=0; i< ci.size(); i++) {
            String[] test = ci.get(i).getTime().split(" ");
            for (int j = 0; j < test.length; j++) {
                Button button = (Button) findViewById(getDaysID(test[j]));
                button.setText(ci.get(i).getClassName());
                button.setBackgroundResource(getButtonColorDrawable(ci.get(i).getClassColor()));
            }
        }

        /* 액션 오버플로우에 액션바들이 무조건 들어가게 한다. (하드웨어로 메뉴키가 있어도 강제 */
        try {
            ViewConfiguration config = ViewConfiguration. get( this);
            Field menuKeyField = ViewConfiguration.class .getDeclaredField( "sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible( true );
                menuKeyField.setBoolean(config, false );
            }
        } catch (Exception ex) {

        }


        /* 시간 얻기 */
        Calendar calendar = Calendar.getInstance();
        long Now = calendar.getTimeInMillis();
        String strNow = calendar.getTime().toString(); //
        // 요일 찾기
        String strDay = getDayString(strNow); // 월요일 : Mon
        // 시간 찾기
        String strTime = getHourString(strNow); // 오후 2시 : 14
        Integer currentHour = Integer.parseInt(strTime);
        /* 오늘 날짜에 해당하는 요일의 테두리색을 변경 */
        TextView mon = (TextView)findViewById(getDayID(strDay)); //Integer형
        mon.setBackgroundResource(R.drawable.day2); //
        if( currentHour >= 8 || currentHour <=22)
        {
            /* 현재 시간에 해당하는 시간 테두리색을 변경 */
            Integer timeId = getTimeID(strTime);
            if(timeId != -1) {
                TextView clock = (TextView) findViewById(timeId); // Integer형
                clock.setBackgroundResource(R.drawable.time2);
            }
        }



        findViewById(R.id.mon1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.mon15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.mon15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.tue15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.tue15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.wed15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.wed15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.thu15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.thu15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.fri15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.fri15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sat15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sat15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun1).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun1).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun2).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun2).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun3).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun3).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun4).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun4).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun5).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun5).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun6).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun6).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun7).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun7).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun8).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun8).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun9).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun9).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun10).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun10).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun11).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun11).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun12).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun12).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun13).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun13).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun14).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun14).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

        findViewById(R.id.sun15).setOnLongClickListener( new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                for(int i=0; i< ci.size(); i++){
                    String[] test = ci.get(i).getTime().split(" ");
                    for (int j = 0; j < test.length; j++) {
                        if(getIdToTime(R.id.sun15).equals(getIdToTime(getDaysID(test[j])))){
                            Intent intentSubActivity =new Intent(MainActivity.this, testActivity.class);
                            intentSubActivity.putExtra("data",ci.get(i));
                            startActivity(intentSubActivity);
                        }
                    }
                }
                return true;
            }
        });

    }

    String getDayString(String str)
    {
        String[] splitedStr = str.split(" ");
        return splitedStr[0];
    }

    Integer getDayID(String str)
    {
        if(str.equals("Mon"))
        {
            return R.id.mon;
        }
        else if(str.equals("Tue"))
        {
            return R.id.tue;
        }
        else if(str.equals("Wed"))
        {
            return R.id.wed;
        }
        else if(str.equals("Thu"))
        {
            return R.id.thu;
        }
        else if(str.equals("Fri"))
        {
            return R.id.fri;
        }
        else if(str.equals("Sat"))
        {
            return R.id.sat;
        }
        else if(str.equals("Sun"))
        {
            return R.id.sun;
        }

        return -1;
    }

    String getHourString(String str)
    {
        String[] splitedStr = str.split(" ");
        String hour = splitedStr[3].substring(0,2);
        return hour;
    }

    Integer getTimeID(String str)
    {
        if(str.equals("8"))
        {
            return R.id.clock8;
        }
        else if(str.equals("9"))
        {
            return R.id.clock9;
        }
        else if(str.equals("10"))
        {
            return R.id.clock10;
        }
        else if(str.equals("11"))
        {
            return R.id.clock11;
        }
        else if(str.equals("12"))
        {
            return R.id.clock12;
        }
        else if(str.equals("13"))
        {
            return R.id.clock13;
        }
        else if(str.equals("14"))
        {
            return R.id.clock14;
        }
        else if(str.equals("15"))
        {
            return R.id.clock15;
        }
        else if(str.equals("16"))
        {
            return R.id.clock16;
        }
        else if(str.equals("17"))
        {
            return R.id.clock17;
        }
        else if(str.equals("18"))
        {
            return R.id.clock18;
        }
        else if(str.equals("19"))
        {
            return R.id.clock19;
        }
        else if(str.equals("20"))
        {
            return R.id.clock20;
        }
        else if(str.equals("21"))
        {
            return R.id.clock21;
        }
        else if(str.equals("22"))
        {
            return R.id.clock22;
        }

        return -1;
    }
    Integer getDaysID(String str)
    {
        if(str.equals("mon1"))
            return R.id.mon1;
        else if(str.equals("mon2"))
            return R.id.mon2;
        else if(str.equals("mon3"))
            return R.id.mon3;
        else if(str.equals("mon4"))
            return R.id.mon4;
        else if(str.equals("mon5"))
            return R.id.mon5;
        else if(str.equals("mon6"))
            return R.id.mon6;
        else if(str.equals("mon7"))
            return R.id.mon7;
        else if(str.equals("mon8"))
            return R.id.mon8;
        else if(str.equals("mon9"))
            return R.id.mon9;
        else if(str.equals("mon10"))
            return R.id.mon10;
        else if(str.equals("mon11"))
            return R.id.mon11;
        else if(str.equals("mon12"))
            return R.id.mon12;
        else if(str.equals("mon13"))
            return R.id.mon13;
        else if(str.equals("mon14"))
            return R.id.mon14;
        else if(str.equals("mon15"))
            return R.id.mon15;
        else if(str.equals("tue1"))
            return R.id.tue1;
        else if(str.equals("tue2"))
            return R.id.tue2;
        else if(str.equals("tue3"))
            return R.id.tue3;
        else if(str.equals("tue4"))
            return R.id.tue4;
        else if(str.equals("tue5"))
            return R.id.tue5;
        else if(str.equals("tue6"))
            return R.id.tue6;
        else if(str.equals("tue7"))
            return R.id.tue7;
        else if(str.equals("tue8"))
            return R.id.tue8;
        else if(str.equals("tue9"))
            return R.id.tue9;
        else if(str.equals("tue10"))
            return R.id.tue10;
        else if(str.equals("tue11"))
            return R.id.tue11;
        else if(str.equals("tue12"))
            return R.id.tue12;
        else if(str.equals("tue13"))
            return R.id.tue13;
        else if(str.equals("tue14"))
            return R.id.tue14;
        else if(str.equals("tue15"))
            return R.id.tue15;
        else if(str.equals("wed1"))
            return R.id.wed1;
        else if(str.equals("wed2"))
            return R.id.wed2;
        else if(str.equals("wed3"))
            return R.id.wed3;
        else if(str.equals("wed4"))
            return R.id.wed4;
        else if(str.equals("wed5"))
            return R.id.wed5;
        else if(str.equals("wed6"))
            return R.id.wed6;
        else if(str.equals("wed7"))
            return R.id.wed7;
        else if(str.equals("wed8"))
            return R.id.wed8;
        else if(str.equals("wed9"))
            return R.id.wed9;
        else if(str.equals("wed10"))
            return R.id.wed10;
        else if(str.equals("wed11"))
            return R.id.wed11;
        else if(str.equals("wed12"))
            return R.id.wed12;
        else if(str.equals("wed13"))
            return R.id.wed13;
        else if(str.equals("wed14"))
            return R.id.wed14;
        else if(str.equals("wed15"))
            return R.id.wed15;
        else if(str.equals("thu1"))
            return R.id.thu1;
        else if(str.equals("thu2"))
            return R.id.thu2;
        else if(str.equals("thu3"))
            return R.id.thu3;
        else if(str.equals("thu4"))
            return R.id.thu4;
        else if(str.equals("thu5"))
            return R.id.thu5;
        else if(str.equals("thu6"))
            return R.id.thu6;
        else if(str.equals("thu7"))
            return R.id.thu7;
        else if(str.equals("thu8"))
            return R.id.thu8;
        else if(str.equals("thu9"))
            return R.id.thu9;
        else if(str.equals("thu10"))
            return R.id.thu10;
        else if(str.equals("thu11"))
            return R.id.thu11;
        else if(str.equals("thu12"))
            return R.id.thu12;
        else if(str.equals("thu13"))
            return R.id.thu13;
        else if(str.equals("thu14"))
            return R.id.thu14;
        else if(str.equals("thu15"))
            return R.id.thu15;
        else if(str.equals("fri1"))
            return R.id.fri1;
        else if(str.equals("fri2"))
            return R.id.fri2;
        else if(str.equals("fri3"))
            return R.id.fri3;
        else if(str.equals("fri4"))
            return R.id.fri4;
        else if(str.equals("fri5"))
            return R.id.fri5;
        else if(str.equals("fri6"))
            return R.id.fri6;
        else if(str.equals("fri7"))
            return R.id.fri7;
        else if(str.equals("fri8"))
            return R.id.fri8;
        else if(str.equals("fri9"))
            return R.id.fri9;
        else if(str.equals("fri10"))
            return R.id.fri10;
        else if(str.equals("fri11"))
            return R.id.fri11;
        else if(str.equals("fri12"))
            return R.id.fri12;
        else if(str.equals("fri13"))
            return R.id.fri13;
        else if(str.equals("fri14"))
            return R.id.fri14;
        else if(str.equals("fri15"))
            return R.id.fri15;
        else if(str.equals("sat1"))
            return R.id.sat1;
        else if(str.equals("sat2"))
            return R.id.sat2;
        else if(str.equals("sat3"))
            return R.id.sat3;
        else if(str.equals("sat4"))
            return R.id.sat4;
        else if(str.equals("sat5"))
            return R.id.sat5;
        else if(str.equals("sat6"))
            return R.id.sat6;
        else if(str.equals("sat7"))
            return R.id.sat7;
        else if(str.equals("sat8"))
            return R.id.sat8;
        else if(str.equals("sat9"))
            return R.id.sat9;
        else if(str.equals("sat10"))
            return R.id.sat10;
        else if(str.equals("sat11"))
            return R.id.sat11;
        else if(str.equals("sat12"))
            return R.id.sat12;
        else if(str.equals("sat13"))
            return R.id.sat13;
        else if(str.equals("sat14"))
            return R.id.sat14;
        else if(str.equals("sat15"))
            return R.id.sat15;
        else if(str.equals("sun1"))
            return R.id.sun1;
        else if(str.equals("sun2"))
            return R.id.sun2;
        else if(str.equals("sun3"))
            return R.id.sun3;
        else if(str.equals("sun4"))
            return R.id.sun4;
        else if(str.equals("sun5"))
            return R.id.sun5;
        else if(str.equals("sun6"))
            return R.id.sun6;
        else if(str.equals("sun7"))
            return R.id.sun7;
        else if(str.equals("sun8"))
            return R.id.sun8;
        else if(str.equals("sun9"))
            return R.id.sun9;
        else if(str.equals("sun10"))
            return R.id.sun10;
        else if(str.equals("sun11"))
            return R.id.sun11;
        else if(str.equals("sun12"))
            return R.id.sun12;
        else if(str.equals("sun13"))
            return R.id.sun13;
        else if(str.equals("sun14"))
            return R.id.sun14;
        else if(str.equals("sun15"))
            return R.id.sun15;
        else
            return R.id.sun15;
    }
    String getIdToTime(Integer num)
    {
        if(num == R.id.mon1)
            return "mon1";
        else if(num == R.id.mon2)
            return "mon2";
        else if(num == R.id.mon3)
            return "mon3";
        else if(num == R.id.mon4)
            return "mon4";
        else if(num == R.id.mon5)
            return "mon5";
        else if(num == R.id.mon6)
            return "mon6";
        else if(num == R.id.mon7)
            return "mon7";
        else if(num == R.id.mon8)
            return "mon8";
        else if(num == R.id.mon9)
            return "mon9";
        else if(num == R.id.mon10)
            return "mon10";
        else if(num == R.id.mon11)
            return "mon11";
        else if(num == R.id.mon12)
            return "mon12";
        else if(num == R.id.mon13)
            return "mon13";
        else if(num == R.id.mon14)
            return "mon14";
        else if(num == R.id.mon15)
            return "mon15";
        else if(num == R.id.tue1)
            return "tue1";
        else if(num == R.id.tue2)
            return "tue2";
        else if(num == R.id.tue3)
            return "tue3";
        else if(num == R.id.tue4)
            return "tue4";
        else if(num == R.id.tue5)
            return "tue5";
        else if(num == R.id.tue6)
            return "tue6";
        else if(num == R.id.tue7)
            return "tue7";
        else if(num == R.id.tue8)
            return "tue8";
        else if(num == R.id.tue9)
            return "tue9";
        else if(num == R.id.tue10)
            return "tue10";
        else if(num == R.id.tue11)
            return "tue11";
        else if(num == R.id.tue12)
            return "tue12";
        else if(num == R.id.tue13)
            return "tue13";
        else if(num == R.id.tue14)
            return "tue14";
        else if(num == R.id.tue15)
            return "tue15";
        else if(num == R.id.wed1)
            return "wed1";
        else if(num == R.id.wed2)
            return "wed2";
        else if(num == R.id.wed3)
            return "wed3";
        else if(num == R.id.wed4)
            return "wed4";
        else if(num == R.id.wed5)
            return "wed5";
        else if(num == R.id.wed6)
            return "wed6";
        else if(num == R.id.wed7)
            return "wed7";
        else if(num == R.id.wed8)
            return "wed8";
        else if(num == R.id.wed9)
            return "wed9";
        else if(num == R.id.wed10)
            return "wed10";
        else if(num == R.id.wed11)
            return "wed11";
        else if(num == R.id.wed12)
            return "wed12";
        else if(num == R.id.wed13)
            return "wed13";
        else if(num == R.id.wed14)
            return "wed14";
        else if(num == R.id.wed15)
            return "wed15";
        else if(num == R.id.thu1)
            return "thu1";
        else if(num == R.id.thu2)
            return "thu2";
        else if(num == R.id.thu3)
            return "thu3";
        else if(num == R.id.thu4)
            return "thu4";
        else if(num == R.id.thu5)
            return "thu5";
        else if(num == R.id.thu6)
            return "thu6";
        else if(num == R.id.thu7)
            return "thu7";
        else if(num == R.id.thu8)
            return "thu8";
        else if(num == R.id.thu9)
            return "thu9";
        else if(num == R.id.thu10)
            return "thu10";
        else if(num == R.id.thu11)
            return "thu11";
        else if(num == R.id.thu12)
            return "thu12";
        else if(num == R.id.thu13)
            return "thu13";
        else if(num == R.id.thu14)
            return "thu14";
        else if(num == R.id.thu15)
            return "thu15";
        else if(num == R.id.fri1)
            return "fri1";
        else if(num == R.id.fri2)
            return "fri2";
        else if(num == R.id.fri3)
            return "fri3";
        else if(num == R.id.fri4)
            return "fri4";
        else if(num == R.id.fri5)
            return "fri5";
        else if(num == R.id.fri6)
            return "fri6";
        else if(num == R.id.fri7)
            return "fri7";
        else if(num == R.id.fri8)
            return "fri8";
        else if(num == R.id.fri9)
            return "fri9";
        else if(num == R.id.fri10)
            return "fri10";
        else if(num == R.id.fri11)
            return "fri11";
        else if(num == R.id.fri12)
            return "fri12";
        else if(num == R.id.fri13)
            return "fri13";
        else if(num == R.id.fri14)
            return "fri14";
        else if(num == R.id.fri15)
            return "fri15";
        else if(num == R.id.sat1)
            return "sat1";
        else if(num == R.id.sat2)
            return "sat2";
        else if(num == R.id.sat3)
            return "sat3";
        else if(num == R.id.sat4)
            return "sat4";
        else if(num == R.id.sat5)
            return "sat5";
        else if(num == R.id.sat6)
            return "sat6";
        else if(num == R.id.sat7)
            return "sat7";
        else if(num == R.id.sat8)
            return "sat8";
        else if(num == R.id.sat9)
            return "sat9";
        else if(num == R.id.sat10)
            return "sat10";
        else if(num == R.id.sat11)
            return "sat11";
        else if(num == R.id.sat12)
            return "sat12";
        else if(num == R.id.sat13)
            return "sat13";
        else if(num == R.id.sat14)
            return "sat14";
        else if(num == R.id.sat15)
            return "sat15";
        else if(num == R.id.sun1)
            return "sun1";
        else if(num == R.id.sun2)
            return "sun2";
        else if(num == R.id.sun3)
            return "sun3";
        else if(num == R.id.sun4)
            return "sun4";
        else if(num == R.id.sun5)
            return "sun5";
        else if(num == R.id.sun6)
            return "sun6";
        else if(num == R.id.sun7)
            return "sun7";
        else if(num == R.id.sun8)
            return "sun8";
        else if(num == R.id.sun9)
            return "sun9";
        else if(num == R.id.sun10)
            return "sun10";
        else if(num == R.id.sun11)
            return "sun11";
        else if(num == R.id.sun12)
            return "sun12";
        else if(num == R.id.sun13)
            return "sun13";
        else if(num == R.id.sun14)
            return "sun14";
        else if(num == R.id.sun15)
            return "sun15";
        else
            return "sun15";
    }

    private Integer getButtonColor(String str){
        if(str.equals("color_button1"))
            return R.id.color_button1;
        else if(str.equals("color_button2"))
            return R.id.color_button2;
        else if(str.equals("color_button3"))
            return R.id.color_button3;
        else if(str.equals("color_button4"))
            return R.id.color_button4;
        else if(str.equals("color_button5"))
            return R.id.color_button5;
        else if(str.equals("color_button6"))
            return R.id.color_button6;
        else if(str.equals("color_button7"))
            return R.id.color_button7;
        else if(str.equals("color_button8"))
            return R.id.color_button8;
        else if(str.equals("color_button9"))
            return R.id.color_button9;
        else if(str.equals("color_button10"))
            return R.id.color_button10;
        else
            return R.id.color_button1;
    }

    public static Integer getButtonColorDrawable(String str){
        if(str.equals("color_button1"))
            return R.drawable.color_button1;
        else if(str.equals("color_button2"))
            return R.drawable.color_button2;
        else if(str.equals("color_button3"))
            return R.drawable.color_button3;
        else if(str.equals("color_button4"))
            return R.drawable.color_button4;
        else if(str.equals("color_button5"))
            return R.drawable.color_button5;
        else if(str.equals("color_button6"))
            return R.drawable.color_button6;
        else if(str.equals("color_button7"))
            return R.drawable.color_button7;
        else if(str.equals("color_button8"))
            return R.drawable.color_button8;
        else if(str.equals("color_button9"))
            return R.drawable.color_button9;
        else if(str.equals("color_button10"))
            return R.drawable.color_button10;
        else
            return R.drawable.color_button1;
    }
}

