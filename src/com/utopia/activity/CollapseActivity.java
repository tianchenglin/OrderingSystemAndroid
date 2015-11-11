package com.utopia.activity;

import com.utopia.utils.ExitApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CollapseActivity extends Activity {
    private Button btnRestart, btnExit;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_activity);   
        btnRestart = (Button) findViewById(R.id.collapse_restart);
        btnExit = (Button) findViewById(R.id.collapse_exit);
        btnRestart.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) { 
            	CollapseActivity.this.finish();
            }
        });
    }
}