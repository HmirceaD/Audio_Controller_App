package com.example.maruta.audiocontroller;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SeekBar vol;
    private AudioManager aud;

    private SeekBar audioBar;

    private Button playButton;
    private Button pauseButton;
    int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mPlay = MediaPlayer.create(this, R.raw.cowboy);

        aud = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVol = aud.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int crrVol = aud.getStreamVolume(AudioManager.STREAM_MUSIC);

        vol = findViewById(R.id.seekBar);
        vol.setMax(maxVol);
        vol.setProgress(crrVol);

        vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                aud.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }


        });

        /*Video Bar*/
        audioBar = findViewById(R.id.videoBar);
        audioBar.setMax(mPlay.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioBar.setProgress(mPlay.getCurrentPosition());
            }
        }, 0, 100);

        audioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPlay.seekTo(progress);
                mPlay.start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);

        playButton.setOnClickListener((View event) -> {
            mPlay.start();
        });

        pauseButton.setOnClickListener((View event)-> {

            if(mPlay.isPlaying()){
                mPlay.pause();
                length = mPlay.getCurrentPosition();
                pauseButton.setText("UNPAUSE");
            }else {
                mPlay.seekTo(length);
                mPlay.start();
                pauseButton.setText("Pause");
            }

        });

    }
}
