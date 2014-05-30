package com.janderson.gtnextbus.navdrawerfragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.janderson.gtnextbus.R;
import com.janderson.gtnextbus.activities.StopActivity;
import com.janderson.gtnextbus.adapters.DestinationAdapter;
import com.janderson.gtnextbus.adapters.FavoriteAdapter;
import com.janderson.gtnextbus.adapters.StopListAdapter;
import com.janderson.gtnextbus.items.StopItem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by JoelAnderson on 5/27/14.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment(){}
    private RelativeLayout favoriteLayout;
    private ListView mRouteList;
    private String header;
    private SharedPreferences preferences;
    private ArrayList<StopItem> favoriteDestinationItems;
    private FavoriteAdapter adapter;
    private int pos = 0;
    private String[] names;
    private String[] routeTags;
    private String[] stopTags;
    private String[] colors;
    private String name;
    private String color;
    private String stop;
    private String route;
    private String routeStopCombo;
    private String[] greenTepStopTags;
    private String[] greenHubStopTags;
    private String[] greenFourteenthStopTags;
    private String[] trolleyMartaStopTags;
    private String[] trolleyHubStopTags;
    private String[] emoryEmoryStopTags;
    private String[] emoryGatechStopTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        header = getResources().getString(R.string.favorite_destinations_header);
        favoriteLayout = (RelativeLayout) getView().findViewById(R.id.fragment_favorite);
        mRouteList = (ListView) getView().findViewById(R.id.favorite_cards);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mRouteList.setPadding(0,72,0,10);
        }
        favoriteDestinationItems = new ArrayList<StopItem>();
        favoriteDestinationItems.add(new StopItem(header, "#000000"));
        preferences = getActivity().getSharedPreferences("saved_favorites", Context.MODE_PRIVATE);
        Map<String, ?> keys = preferences.getAll();
        names = new String[keys.size()];
        routeTags = new String[keys.size()];
        stopTags = new String[keys.size()];
        colors = new String[keys.size()];
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Set<String> item = (Set<String>) entry.getValue();
            Object[] itemArray = item.toArray();
            for (int i = 0; i < itemArray.length; i++) {
                if (itemArray[i].toString().startsWith("?")) {
                    name = itemArray[i].toString().substring(1);
                    names[pos] = name;
                } else if (itemArray[i].toString().startsWith("*")) {
                    route = itemArray[i].toString().substring(1);
                    routeTags[pos] = route;
                } else if (itemArray[i].toString().startsWith("$")) {
                    stop = itemArray[i].toString().substring(1);
                    stopTags[pos] = stop;
                } else if (itemArray[i].toString().startsWith("#")) {
                    color = itemArray[i].toString();
                    colors[pos] = color;
                }
            }
            routeStopCombo = route.concat(stop);
            addDestination();
            favoriteDestinationItems.add(new StopItem(name, color));
            pos++;
        }
        adapter = new FavoriteAdapter(getActivity().getApplicationContext(),
                favoriteDestinationItems);
        mRouteList.setAdapter(adapter);
        mRouteList.setOnItemClickListener(new StopListClickListener());
        mRouteList.setOnScrollListener(new AbsListView.OnScrollListener() {

            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                final int currentFirstVisibleItem = mRouteList.getFirstVisiblePosition();
                if (currentFirstVisibleItem > mLastFirstVisibleItem)
                {
                    getActivity().getActionBar().hide();
                }
                else if (currentFirstVisibleItem < mLastFirstVisibleItem)
                {
                    getActivity().getActionBar().show();
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;
            }
        });
    }

    private class StopListClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Intent intent = null;
        String[] strings = null;
        ArrayList<String> stringArrayList = null;
        switch (position) {
            case 0:
                break;
            default:
                intent = new Intent(getActivity(), StopActivity.class);
                strings = new String [] {names[position - 1],routeTags[position - 1], stopTags[position - 1], colors[position -1]};
                intent.putExtra("extra", strings);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating activity");
        }
    }

    public boolean addDestination() {
        greenTepStopTags = getResources().getStringArray(R.array.green_tep_stop_titles);
        greenHubStopTags = getResources().getStringArray(R.array.green_hub_stop_titles);
        greenFourteenthStopTags = getResources().getStringArray(R.array.green_fourteenth_stop_titles);
        trolleyHubStopTags = getResources().getStringArray(R.array.trolley_hub_stop_titles);
        trolleyMartaStopTags = getResources().getStringArray(R.array.trolley_marta_stop_titles);
        emoryEmoryStopTags = getResources().getStringArray(R.array.emory_emory_stop_titles);
        emoryGatechStopTags = getResources().getStringArray(R.array.emory_gatech_stop_titles);
        for (String tag : greenTepStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To TEP- \n" + name;
                return true;
            }
        }
        for (String tag : greenHubStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To Hub- \n" + name;
                return true;
            }
        }
        for (String tag : greenFourteenthStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To 14th Street- \n" + name;
                return true;
            }
        }
        for (String tag : trolleyHubStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To Hub- \n" + name;
                return true;
            }
        }
        for (String tag : trolleyMartaStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To MARTA- \n" + name;
                return true;
            }
        }
        for (String tag : emoryEmoryStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To Emory- \n" + name;
                return true;
            }
        }
        for (String tag : emoryGatechStopTags) {
            if (tag.contains(routeStopCombo)) {
                name = "-To Georgia Tech- \n" + name;
                return true;
            }
        }
        return false;
    }
}