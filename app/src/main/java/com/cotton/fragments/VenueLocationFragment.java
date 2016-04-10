package com.cotton.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cotton.R;

public class VenueLocationFragment extends Fragment {

    private ListView listView;
    private RelativeLayout header;
    private RelativeLayout locationLayout;

    public VenueLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.listview_main, container, false);

        listView = (ListView)view.findViewById(R.id.listview);
        header = (RelativeLayout)getActivity().getLayoutInflater().inflate(R.layout.venue_location_main, null, false);
        listView.addHeaderView(header);

        listView.setAdapter(null);
        listView.setSelection(0);

        locationLayout = (RelativeLayout)header.findViewById(R.id.location_layout);
        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:38.940379,1.2364647,15?q=Cotton Beach Club Ibiza");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        return view;
    }

}