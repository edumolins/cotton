package com.cotton.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cotton.R;
import com.cotton.objects.EventHolder;
import com.cotton.objects.ObjEvent;

import java.util.ArrayList;

public class EventsAdapter extends ArrayAdapter<ObjEvent> {

    private Activity context;
    private ArrayList<ObjEvent> datos;

    public EventsAdapter(Activity ctx, ArrayList<ObjEvent> datos) {
        super(ctx, R.layout.event_row, datos);
        this.datos = new ArrayList<ObjEvent>(datos);
        this.context = ctx;
    }
     
    public int getCount() {
        return datos.size();
    }
    
    public ObjEvent getItem(int position) {
        return datos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    public void setList(ArrayList<ObjEvent> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
    	final EventHolder eventHolder;
    	View item = convertView;
    	if(item == null)
		{
    		LayoutInflater inflater= context.getLayoutInflater();
			View viewGroup = inflater.inflate(R.layout.event_row, parent, false);
			//using the ViewHolder pattern to reduce lookups
            eventHolder = new EventHolder(
                    (TextView)viewGroup.findViewById(R.id.day),
            		(TextView)viewGroup.findViewById(R.id.month),
            		(TextView)viewGroup.findViewById(R.id.name),
                    (TextView)viewGroup.findViewById(R.id.location),
                    (ImageView)viewGroup.findViewById(R.id.viewmore),
                    (RelativeLayout)viewGroup.findViewById(R.id.description_layout),
                    (TextView)viewGroup.findViewById(R.id.description),
                    (RelativeLayout)viewGroup.findViewById(R.id.book_layout),
                    (ImageView)viewGroup.findViewById(R.id.image));
            viewGroup.setTag(eventHolder);
            item = viewGroup;
		}
		else
		{
			 eventHolder = (EventHolder)convertView.getTag();
        	 item = convertView;
		}
    	try{

            eventHolder.getViewEvent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(eventHolder.getDescriptionLayoutEvent().getVisibility() == View.VISIBLE){
                        eventHolder.getViewEvent().setImageResource(R.drawable.more_button);
                        Animation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(100);
                        anim.setFillAfter(true);
                        eventHolder.getDescriptionLayoutEvent().setAnimation(anim);
                        eventHolder.getDescriptionLayoutEvent().setVisibility(View.GONE);

                    }
                    else{
                        eventHolder.getViewEvent().setImageResource(R.drawable.less_button);
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(1000);
                        anim.setFillAfter(true);
                        eventHolder.getDescriptionLayoutEvent().setAnimation(anim);
                        eventHolder.getDescriptionLayoutEvent().setVisibility(View.VISIBLE);
                    }

                }
            });
	    	/*
	    	    Codi on es fa set de les variables de cada row

	    	 */


    	}catch(Exception e){    		
    		Log.e("Adapter error flights", e.getMessage());
    	}
    	return item;
    }
}
