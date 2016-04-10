package com.cotton.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cotton.R;

public class VenueWelcomeFragment extends Fragment {

    private ListView listView;
    private RelativeLayout header;
    private ImageView playVideo;
    String urlVideo = "https://vimeo.com/132683131";
    public VenueWelcomeFragment() {
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

        listView= (ListView)view.findViewById(R.id.listview);
        header = (RelativeLayout)getActivity().getLayoutInflater().inflate(R.layout.venue_welcome_main, null, false);
        listView.addHeaderView(header);

        listView.setAdapter(null);
        listView.setSelection(0);

        playVideo = (ImageView)header.findViewById(R.id.play_video);
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlVideo));
                startActivity(intent);
            }
        });
        return view;
    }



}