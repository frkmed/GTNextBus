package com.janderson.gtnextbus.navdrawerfragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.janderson.gtnextbus.R;
import com.janderson.gtnextbus.activities.StopListActivity;
import com.janderson.gtnextbus.adapters.DestinationAdapter;
import com.janderson.gtnextbus.items.RouteItem;

import java.util.ArrayList;

public class TrolleyFragment extends Fragment {

    public TrolleyFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_trolley, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Tech Trolley");
        String[] destinations = getResources().getStringArray(R.array.trolley_destinations);
        ListView mRouteList = (ListView) getView().findViewById(R.id.trolley_cards);
        if (savedInstanceState != null) {
            mRouteList.setLayoutAnimation(null);
        }
        ArrayList<RouteItem> trolleyDestinationItems = new ArrayList<RouteItem>();
        trolleyDestinationItems.add(new RouteItem(destinations[1], R.drawable.marta, true));
        trolleyDestinationItems.add(new RouteItem(destinations[2], R.drawable.hub, true));
        DestinationAdapter adapter = new DestinationAdapter(getActivity().getApplicationContext(),
                trolleyDestinationItems, false);
        mRouteList.setAdapter(adapter);
        mRouteList.setOnItemClickListener(new StopClickListener());
    }

    private class StopClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Intent intent = null;
        String[] strings;
        String[] stops;
        String[] stopTags;
        switch (position) {
            case 0:
                intent = new Intent(getActivity(), StopListActivity.class);
                strings = new String [] {"To MARTA Station", "#FFBB33", "trolley"};
                stops = getResources().getStringArray(R.array.trolley_marta_stops);
                stopTags = getResources().getStringArray(R.array.trolley_marta_stop_tags);
                intent.putExtra("stopTags", stopTags);
                intent.putExtra("stops", stops);
                intent.putExtra("extra", strings);
                break;
            case 1:
                intent = new Intent(getActivity(), StopListActivity.class);
                strings = new String [] {"To Transit Hub", "#FFBB33", "trolley"};
                stops = getResources().getStringArray(R.array.trolley_hub_stops);
                stopTags = getResources().getStringArray(R.array.trolley_hub_stop_tags);
                intent.putExtra("stopTags", stopTags);
                intent.putExtra("stops", stops);
                intent.putExtra("extra", strings);
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating activity");
        }
    }
}
