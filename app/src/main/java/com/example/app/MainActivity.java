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
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    public static String path;
    public static final boolean DEBUG = true;
    private Button playb;
    private Button pauseb;
    private Button stopb;
    private Button browseb;
    private SeekBar sb;
    private MediaPlayer mp;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playb = (Button) findViewById(R.id.playb);
        pauseb = (Button) findViewById(R.id.pauseb);
        stopb = (Button) findViewById(R.id.stopb);
        browseb = (Button) findViewById(R.id.browseb);
        sb = (SeekBar) findViewById(R.id.sb);

        // Runnable responsible for updating the UI with playing progress.
        r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sb.setProgress((mp == null) ?  0 : mp.getCurrentPosition());
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        Log.e("MedaiPlayerThread", "ProgressFAIL", e);
                    }
                }
            }
        };
        Thread t = new Thread(r);
        //t.start();

        playb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp == null) {
                    return;
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
                }
                mp.seekTo(0);
            }
        });

        browseb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), FileBrowserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b && mp != null) {
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

    @Override
    protected void onResume() {
        super.onResume();

        try {
            //Bundle extras = getIntent().getExtras();
            //String path = extras.getString("path");
            if (mp == null) {
                mp = MediaPlayer.create(getBaseContext(), Uri.parse(path));
            } else {
                mp.reset();
                mp.setDataSource(getBaseContext(), Uri.parse(path));
            }

            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                }
            });
            sb.setProgress(0);
            sb.setMax(mp.getDuration());

        } catch (Exception e) {
            if (DEBUG) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
