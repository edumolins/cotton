package com.cotton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.cotton.services.MusicService;
import com.cotton.ui.AvenirTextView;
import com.cotton.utils.Constants;

public class PlayerActivity extends FragmentActivity {


    public static ImageView imgSong;
    public static AvenirTextView titleSong;
    public static AvenirTextView artistSong;
    SeekBar seekBar;
    ImageView prevButton;
    ImageView playButton;
    ImageView nextButton;

    Intent serviceIntent;

    MusicService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main);
        serviceIntent = new Intent(this, MusicService.class);

        imgSong = (ImageView)findViewById(R.id.img_song);
        titleSong = (AvenirTextView)findViewById(R.id.title_song);
        artistSong = (AvenirTextView)findViewById(R.id.artist_song);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        seekBar.setEnabled(false);
        new MyCountDown(100000, 1000).start();

        setInfo();

        prevButton = (ImageView)findViewById(R.id.prev_button);
        playButton = (ImageView)findViewById(R.id.play_button);
        nextButton = (ImageView)findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong();
            }
        });
    }



    public class MyCountDown extends CountDownTimer {

        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            seekBar.setMax((int) millisInFuture);

        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onTick(long millisUntilFinished) {
            long timeRemaining = millisUntilFinished;
            seekBar.setProgress((int) (100000 - timeRemaining));
            //Log.i(TAG, "Time tick: " + millisUntilFinished);
        }
    }

    public void playSong(){


    }

    public void stopSong(){

    }

    public void nextSong(){

        Constants.songPosition++;
        serviceIntent.putExtra("sentAudioLink",Constants.songs[Constants.songPosition]);
        serviceIntent.putExtra("fromActivity","true");
        startService(serviceIntent);
    }

    public void previousSong(){
        Constants.songPosition--;
        serviceIntent.putExtra("sentAudioLink", Constants.songs[Constants.songPosition]);
        serviceIntent.putExtra("fromActivity","true");
        startService(serviceIntent);
    }

    public void setInfo(){
        Bitmap bitmap = BitmapFactory.decodeByteArray(Constants.songImage, 0, Constants.songImage.length);
        imgSong.setImageBitmap(bitmap);
        titleSong.setText(Constants.songTitle);
        artistSong.setText(Constants.songArtist);
    }
    public void enableButtons(){

    }
}
