package com.cotton.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cotton.R;

public class VenueMenuFragment extends Fragment {

    private ListView listView;
    private RelativeLayout header;

    public VenueMenuFragment() {
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
        header = (RelativeLayout)getActivity().getLayoutInflater().inflate(R.layout.venue_menu_main, null, false);
        listView.addHeaderView(header);

        listView.setAdapter(null);
        listView.setSelection(0);

        return view;
    }

}