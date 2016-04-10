package com.cotton;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cotton.utils.ConnectManager;

import org.json.JSONArray;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        //ConcurrentAsyncTask.execute(new GetMetaDataMp3(this).execute());
        //new SendGroup().execute();
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
            }*/
            //STATUS OK

                try{
                    JSONArray jsonArray = new JSONArray(result_group);
                    for (int i=0; i < jsonArray.length(); i++){
                        //Constants.mapGroups.put(jsonArray.getJSONObject(i).getString("key"), jsonArray.getJSONObject(i).getString("value"));
                    }
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){

                }
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... arg) {
            try {
                result_group = ConnectManager.callMethod();
            } catch (Exception e) {
            }

            return true;
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        //stopService(serviceIntent);
    }
}
