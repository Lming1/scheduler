package com.capston.googolhkl.scheduler;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.capston.googolhkl.schduler.R;


/**
 * Created by googolhkl on 2016. 11. 27..
 */

public class MakerActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker);


        findViewById(R.id.maker_hkl).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"hkl",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.maker_bwk).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"bwk",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.maker_bgu).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"bgu",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.maker_ksm).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"ksm",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.maker_edg).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"edg",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.maker_scy).setOnClickListener( new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MakerActivity.this ,"scy",Toast.LENGTH_LONG).show();
            }
        });
    }
}
