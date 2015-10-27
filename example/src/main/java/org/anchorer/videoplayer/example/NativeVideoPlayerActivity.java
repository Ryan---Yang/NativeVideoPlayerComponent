package org.anchorer.videoplayer.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import org.anchorer.videoplayer.BaseMediaControllerHolder;
import org.anchorer.videoplayer.BaseNativeVideoPlayerActivity;
import org.anchorer.videoplayer.NativeMediaController;

/**
 * 自定义UI视频播放器页面Demo。
 *
 * Created by Anchorer on 2014/9/23.
 */
public class NativeVideoPlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_demo_activity);

        // 如果有通过Intent传入的其他数据，可以在该Activity的onCreate()方法中直接使用父类的Intent对象
        // ...
        // int videoId = mIntent.getIntExtra("videoId", 0);
        // ...

        // 如果有需要做一些监听的，可以使用父类中的MediaPlayer对象设置监听器
        // ...
        /*
        // 设置缓冲状态的监听器
        mPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        */
        // ...
    }
}
