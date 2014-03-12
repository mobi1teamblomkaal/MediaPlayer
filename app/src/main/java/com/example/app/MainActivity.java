package com.example.app;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends ActionBarActivity {
    Button playb;
    Button pauseb;
    Button stopb;
    SeekBar sb;
    MediaPlayer mp;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playb = (Button) findViewById(R.id.playb);
        pauseb = (Button) findViewById(R.id.pauseb);
        stopb = (Button) findViewById(R.id.stopb);
        sb = (SeekBar) findViewById(R.id.sb);

        r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!mp.isPlaying()) {
                        mp.seekTo(0);
                    }
                    sb.setProgress(mp.getCurrentPosition());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        Log.e("MedaiPlayerThread", "ProgressFAIL", e);
                    }
                }
            }
        };

        playb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp == null) {
                    mp = MediaPlayer.create(getBaseContext(), Uri.parse("http://gototen.dk/wp-content/uploads/2013/12/dont-mess-with-my-man.mp3"));
                    sb.setMax(mp.getDuration());
                    Thread t = new Thread(r);
                    t.start();
                }
                mp.start();
            }
        });
        pauseb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp == null) {
                    return;
                }
                if (!mp.isPlaying()) {
                    mp.start();

                } else {
                    mp.pause();
                }
            }
        });
        stopb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp == null) {
                    return;
                }
                if (mp.isPlaying()) {
                    mp.pause();
                    mp.seekTo(0);
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mp.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Should do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Should do nothing
            }
        });
    }
}
