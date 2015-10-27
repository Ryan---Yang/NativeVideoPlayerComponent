package org.anchorer.videoplayer.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 入口。
 *
 * Created by Anchorer on 2014/9/26.
 */
public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        Intent intent = new Intent(this, NativeVideoPlayerActivity.class);
        intent.putExtra("path", "http://vodcdn.video.taobao.com/oss/ali-video/8bf47e75c180cc4743aac981884ec19e/video.mp4");
        startActivity(intent);
        */
        setContentView(R.layout.main_activity);
        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoDemoAcitity();
            }
        });
    }

    private void startVideoDemoAcitity(){
        startActivity(new Intent(this, NativeVideoPlayerActivity.class));
    }
}
