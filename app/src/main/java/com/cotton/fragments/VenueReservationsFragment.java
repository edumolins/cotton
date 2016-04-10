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

public class VenueReservationsFragment extends Fragment {

    private ListView listView;
    private RelativeLayout header;

    RelativeLayout bookLayout;
    RelativeLayout phoneLayout;

    String urlBooking = "https://www.resdiary.com/Restaurant/CottonBeachClub1";
    public VenueReservationsFragment() {
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
        header = (RelativeLayout)getActivity().getLayoutInflater().inflate(R.layout.venue_reservations_main, null, false);
        listView.addHeaderView(header);

        listView.setAdapter(null);
        listView.setSelection(0);

        bookLayout = (RelativeLayout)header.findViewById(R.id.book_layout);
        bookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBooking));
                startActivity(intent);
            }
        });

        phoneLayout = (RelativeLayout)header.findViewById(R.id.phone_layout);
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:34971806180";
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                startActivity(callIntent);
            }
        });

        return view;
    }



}