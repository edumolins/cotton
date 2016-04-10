/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cotton.services;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayerService extends Service {
    public MediaPlayerService() {
    }
    MediaPlayer player;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
        try {
            String url = "http://ia601506.us.archive.org/27/items/DaliborDadoffTheSoundOfCottonBeachClubVol.001IbizaGlobalRadio/DaliborDadoff-TheSoundOfCottonBeachClubVol.009ibizaGlobalRadio.mp3"; // your URL here
            //String url = "http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3";
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepareAsync(); // might take long! (for buffering, etc)
        }catch(IOException e){
                String x = "";
        }
       // player = MediaPlayer.create(this, R.raw.song); //create a folder in res folder named as raw and add mp3 as song
       // player.setLooping(false);
    }

    @Override
    public void onStart(Intent intent, int startId) {
// Perform your long running operations here.
        player.start();

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        player.stop();

        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}