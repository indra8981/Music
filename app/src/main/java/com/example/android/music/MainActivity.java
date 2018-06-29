package com.example.android.music;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int clicked=1;
    ImageView img;
    SeekBar sk;
    TextView tv;
    MediaPlayer mp;
    AudioManager am;
    Timer tm;
    TimerTask tk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView) findViewById(R.id.animated);
        mp=MediaPlayer.create(this,R.raw.laughter);
        tv=(TextView)findViewById(R.id.textView);
        am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        sk=(SeekBar)findViewById(R.id.seekBar);
        sk.setMax(mp.getDuration());
        tm=new Timer();
        tk=new TimerTask() {
            @Override
            public void run() {
                //mp.pause();
                sk.setProgress(mp.getCurrentPosition());
                long x=mp.getCurrentPosition()/1000;
                long m=x/60;
                long sec=x%60;
                tv.setText(Long.toString(m)+"."+Long.toString(m)+" S");
                Log.d("Progress at tasker",Integer.toString(mp.getCurrentPosition()));
                //mp.start();
            }
        };
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Progress",Integer.toString(progress));
                if(fromUser)
                    mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mp.start();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(clicked==1) {
                    img.setImageDrawable((getDrawable(R.drawable.play_pause)));
                    clicked*=-1;
                    mp.start();
                    //tm=new Timer();
                    tm.scheduleAtFixedRate(tk,0,100);
                }else{
                    img.setImageDrawable((getDrawable(R.drawable.pause_play)));
                    tm.cancel();
                    tm.purge();
                    //tk.cancel();
                    //tk.purge();
                    clicked*=-1;
                    mp.pause();
                }
                Drawable animation = img.getDrawable();
                if (animation instanceof Animatable) {
                    ((Animatable) animation).start();
                }
            }
        });
    }

}
