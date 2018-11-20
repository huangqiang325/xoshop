package com.kulian.comm.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.kulian.R;

/**
 * Created by xiaoqiang on 2017/12/11.
 */

public class BackMusicServiceZhua extends Service {
    private MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mp=MediaPlayer.create(this,R.raw.musci);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);

    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mp.start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

}
