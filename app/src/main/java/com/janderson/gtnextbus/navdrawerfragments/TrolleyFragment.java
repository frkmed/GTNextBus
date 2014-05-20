package com.janderson.gtnextbus.navdrawerfragments;

/**
 * Created by JoelAnderson on 5/15/14.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.content.Intent;

import com.janderson.gtnextbus.activities.StopListActivity;
import com.janderson.gtnextbus.adapters.DestinationAdapter;
import com.janderson.gtnextbus.R;
import com.janderson.gtnextbus.items.RouteItem;
import com.janderson.gtnextbus.activities.StopActivity;

import java.util.ArrayList;

public class TrolleyFragment extends Fragment {

    private RelativeLayout trolleyDestinationLayout;
    private ListView mRouteList;
    private String[] destinations;
    private ArrayList<RouteItem> trolleyDestinationItems;
    private DestinationAdapter adapter;

    public TrolleyFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trolley, container, false);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        destinations = getResources().getStringArray(R.array.trolley_destinations);
        trolleyDestinationLayout = (RelativeLayout) getView().findViewById(R.id.fragment_trolley);
        mRouteList = (ListView) getView().findViewById(R.id.trolley_cards);
        trolleyDestinationItems = new ArrayList<RouteItem>();
        trolleyDestinationItems.add(new RouteItem(destinations[0]));
        trolleyDestinationItems.add(new RouteItem(destinations[1]));
        trolleyDestinationItems.add(new RouteItem(destinations[2]));
        adapter = new DestinationAdapter(getActivity().getApplicationContext(),
                trolleyDestinationItems);
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
        String[] strings =  null;
        String[] stops = null;
        String[] stopTags = null;
        switch (position) {
            case 0:
                break;
            case 1:
                intent = new Intent(getActivity(), StopListActivity.class);
                strings = new String [] {"To MARTA Station", "#FFBB33", "trolley"};
                stops = getResources().getStringArray(R.array.trolley_marta_stops);
                stopTags = getResources().getStringArray(R.array.trolley_marta_stop_tags);
                intent.putExtra("stopTags", stopTags);
                intent.putExtra("stops", stops);
                intent.putExtra("extra", strings);
                break;
            case 2:
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
