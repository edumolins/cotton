

package com.cotton.services;


import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.IBinder;
import android.widget.Toast;

import com.cotton.PlayerActivity;
import com.cotton.utils.Constants;

import java.io.IOException;
import java.util.HashMap;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener,
        OnPreparedListener, OnErrorListener, OnSeekCompleteListener, OnInfoListener,    OnBufferingUpdateListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    MediaMetadataRetriever metaRetriever= new MediaMetadataRetriever();
    private String sntAudioLink;
    private String fromActivity;

    @Override
    public void onCreate() {

        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        //mediaPlayer.reset();
        //Toast.makeText(this, "Create ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            mediaPlayer.reset();

        if (!mediaPlayer.isPlaying()) {
            try {
                sntAudioLink = intent.getExtras().getString("sentAudioLink");
                fromActivity = intent.getExtras().getString("fromActivity");
                mediaPlayer.setDataSource(sntAudioLink);

                // prepare media player
                mediaPlayer.prepareAsync();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(NullPointerException e ){
                e.printStackTrace();
            }
        }

        // Start Stick return means service will explicitly continue until user
        // ends it
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        String x = "";
        return null;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this,
                        "Media Error: Not valid for Progressive Playback " + extra,
                        Toast.LENGTH_SHORT).show();
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "Media Error: Server Died!! " + extra,
                        Toast.LENGTH_SHORT).show();
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "Media Error: Unknown " + extra,
                        Toast.LENGTH_SHORT).show();

        }

        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        playMedia();
        metaRetriever.setDataSource(sntAudioLink, new HashMap<String, String>());
        Constants.songTitle = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        Constants.songArtist = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        Constants.songImage = metaRetriever.getEmbeddedPicture();

        if(fromActivity.equals("true")){
            Bitmap bitmap = BitmapFactory.decodeByteArray(Constants.songImage, 0, Constants.songImage.length);
            PlayerActivity.imgSong.setImageBitmap(bitmap);
            PlayerActivity.titleSong.setText(Constants.songTitle);
            PlayerActivity.artistSong.setText(Constants.songArtist);
        }

    }

    private void playMedia() {
        // TODO Auto-generated method stub
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void stopMedia() {
        // TODO Auto-generated method stub
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public int getDuration() {
        // TODO Auto-generated method stub
        if (mediaPlayer.isPlaying()) {
            return mediaPlayer.getDuration();
        }
        else
            return 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        stopMedia();
        stopSelf();

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return false;
    }

}