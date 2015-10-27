package org.anchorer.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by huaishu on 15/10/26.
 */
public class CustomSurfaceView extends FrameLayout implements MediaPlayer.OnPreparedListener, NativeMediaController.MediaPlayerControl, NativeMediaController.MediaControllerGenerator {

    private VideoView mVideoView;
    private Activity mContext;
    private NativeMediaController mController;
    private int mCurrentPosition;
    private boolean isFullScreen = false;
    private final String defaultPath = "http://vodcdn.video.taobao.com/oss/ali-video/8bf47e75c180cc4743aac981884ec19e/video.mp4";

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_surface_view, this);
        mVideoView = (VideoView) findViewById(R.id.video_surface);

        // TODO: 15/10/26
        mCurrentPosition = 0;

        mContext = (Activity)context;

        mController = new NativeMediaController(context);
        mController.setUIGenerator(this);

        mVideoView.setVideoURI(Uri.parse(defaultPath));
        mVideoView.setOnPreparedListener(this);
        mVideoView.start();
    }


    @Override
    public BaseMediaControllerHolder generateMediaController() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.video_native_media_controler_custom, null);

        BaseMediaControllerHolder holder = new BaseMediaControllerHolder();
        holder.parentLayout = view;
        holder.pauseButton = (ImageButton) view.findViewById(R.id.video_native_media_controller_custom_btn_start);
        holder.currentTimeView = (TextView) view.findViewById(R.id.video_native_media_controller_custom_currenttime);
        holder.totalTimeView = (TextView) view.findViewById(R.id.video_native_media_controller_custom_totaltime);
        holder.seekbar = (SeekBar) view.findViewById(R.id.video_native_media_controller_custom_seekbar);
        holder.fullScreenButton = (ImageButton) view.findViewById(R.id.video_native_media_controller_custom_btn_unfullscreen);
        holder.pauseResId = R.drawable.selector_video_btn_pause;
        holder.startResId = R.drawable.selector_video_btn_start;
        holder.fullscreenResId = R.drawable.selector_video_btn_fullscreen;
        holder.unfullscreenResId = R.drawable.selector_video_btn_unfullscreen;

        return holder;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mController.show();
        return false;
    }

    // Implement VideoMediaCController.MediaPlayerControl
    @Override
    public void start() {
        if (mVideoView != null) {
            mVideoView.start();
        }
    }

    @Override
    public void pause() {
        if (mVideoView != null)
            mVideoView.pause();
    }

    @Override
    public int getDuration() {
        if (mVideoView != null) {
            return mVideoView.getDuration();
        } else
            return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        } else
            return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (mVideoView != null) {
            mVideoView.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying() {
        return mVideoView != null && mVideoView.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean isFullScreen() {
        return isFullScreen;
    }

    @Override
    public void toggleFullScreen() {
        if (isFullScreen) {
            exitFullScreen();
            isFullScreen = false;
        }
        else {
            toFullScreen();
            isFullScreen = true;
        }
    }

    // End Implement VideoMediaController.MediaPlayerControl

    private int VideoHeight = 0;
    public void toFullScreen() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        VideoHeight = lp.height;

        FrameLayout.LayoutParams roomvideoLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//		roomvideoLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(roomvideoLayoutParams);

        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void exitFullScreen() {
        final WindowManager.LayoutParams attrs = mContext.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext.getWindow().setAttributes(attrs);
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FrameLayout.LayoutParams roomvideoLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, VideoHeight);
//		roomvideoLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(roomvideoLayoutParams);
    }


    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        mController.setMediaPlayer(this);
        mController.setAnchorView(this);
        mController.show();
        mVideoView.start();
        this.seekTo(mCurrentPosition);
        mController.updatePausePlay();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
