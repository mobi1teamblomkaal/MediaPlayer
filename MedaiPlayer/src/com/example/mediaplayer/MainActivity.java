package com.example.mediaplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends Activity {
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

		// Runnable responsible for updating the UI with playing progress.
		r = new Runnable() {
			@Override
			public void run() {
				while (true) {
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
					mp = MediaPlayer.create(
							getBaseContext(),
							Uri.parse("http://gototen.dk/wp-content/uploads/2013/12/dont-mess-with-my-man.mp3"));
					mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
						}
					});
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
				}
				mp.seekTo(0);
			}
		});

		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					mp.seekTo(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Should do nothing
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Should do nothing
			}
		});
	}
}
