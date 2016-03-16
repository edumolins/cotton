package com.cotton;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cotton.adapters.EventsAdapter;
import com.cotton.objects.ObjEvent;

import java.util.ArrayList;

public class EventsActivity extends Activity {

    private ArrayList<ObjEvent> eventsDatos = new ArrayList<>();
    private EventsAdapter eventsAdapter;
    private ListView listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_main);

        ImageView back = (ImageView)findViewById(R.id.close);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listEvents= (ListView)findViewById(R.id.list_events);
        new GetFlights().execute();
    }

    private class GetFlights extends AsyncTask<String, Void, Boolean> {

        public GetFlights() {
            super();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try{
                for (int i =0; i < 5; i++){
                    eventsDatos.add(new ObjEvent(1));
                }
                eventsAdapter = new EventsAdapter(EventsActivity.this, eventsDatos);
                listEvents.setAdapter(eventsAdapter);
                listEvents.setSelection(0);

            }catch (Exception e){
                Log.e("Error list locations", e.getMessage());
            }
            super.onPostExecute(result);

        }

    }

}
