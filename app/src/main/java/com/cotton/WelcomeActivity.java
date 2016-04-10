package com.cotton;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cotton.services.MusicService;
import com.cotton.ui.AvenirTextView;
import com.cotton.utils.Constants;

public class WelcomeActivity extends Activity {

    private ImageView eventsButton;
    private ImageView cameraButton;
    private ImageView playerButton;
    private AvenirTextView venue1;
    private AvenirTextView venue2;

    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        Constants.songPosition = 0;
        serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("sentAudioLink", Constants.songs[Constants.songPosition]);
        serviceIntent.putExtra("fromActivity", "false");
        startService(serviceIntent);

        eventsButton = (ImageView)findViewById(R.id.events_button);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
                startActivity(intent);
            }
        });

        cameraButton = (ImageView)findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoIntentActivity.class);
                startActivity(intent);
                // create Intent to take a picture and return control to the calling application

            }
        });

        playerButton = (ImageView)findViewById(R.id.player_button);
        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                startActivity(intent);
            }
        });

        venue1 = (AvenirTextView)findViewById(R.id.beach_club);
        venue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VenueActivity.class);
                startActivity(intent);
            }
        });
        venue2 = (AvenirTextView)findViewById(R.id.lounge_club);
        venue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityGallery.class);
                startActivity(intent);
            }
        });
    }



    private class SendGroup extends AsyncTask<String, Void, Boolean> {

        public String result_group;
        public SendGroup(){
            super();
        }

        @Override
        protected void onPostExecute(Boolean result) {
           /* if(result_group.contains(Constants.PLEASE_ERROR)){
                //Toast.makeText(getApplicationContext(), getString(R.string.group_name_invalid), Toast.LENGTH_LONG).show();
            }
            //STATUS OK
            else{
                try{
                    Constants.mapGroups.clear();
                    JSONArray jsonArray = new JSONArray(result_group);
                    for (int i=0; i < jsonArray.length(); i++){
                        Constants.mapGroups.put(jsonArray.getJSONObject(i).getString("key"), jsonArray.getJSONObject(i).getString("value"));
                    }
                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){

                }
            }*/
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... arg) {
            try {
                //result_group = ConnectManager.callEnvironmentGroups(Constants.URL_GET_ENV_CONFIGURATION, "");
            } catch (Exception e) {
            }

            return true;
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(serviceIntent);
    }
}
