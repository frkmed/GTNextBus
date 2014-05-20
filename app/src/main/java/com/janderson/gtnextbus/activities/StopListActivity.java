package com.janderson.gtnextbus.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.janderson.gtnextbus.R;
import com.janderson.gtnextbus.adapters.StopListAdapter;
import com.janderson.gtnextbus.items.RouteItem;
import com.janderson.gtnextbus.items.StopItem;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;


public class StopListActivity extends Activity {

    private String[] strings;
    private String title;
    private String color;
    private RelativeLayout stopLayout;
    private ListView stopList;
    private ArrayList<StopItem> stopItems;
    private StopListAdapter adapter;
    private String[] stops;
    private String routeTag;
    private String[] stopTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        int actionBarColor = Color.parseColor("#FFBB33");
        tintManager.setStatusBarTintColor(actionBarColor);
        Intent intent = getIntent();
        strings = intent.getStringArrayExtra("extra");
        stops = intent.getStringArrayExtra("stops");
        stopTags = intent.getStringArrayExtra("stopTags");
        title = strings[0];
        color = strings[1];
        routeTag = strings[2];
        stopLayout = (RelativeLayout) this.findViewById(R.id.activity_stop_list);
        stopList = (ListView) this.findViewById(R.id.stop_list_cards);
        stopItems = new ArrayList<StopItem>();
        stopItems.add(new StopItem(title));
        for (int i = 0; i < stops.length; i++) {
            stopItems.add(new StopItem(stops[i]));
        }
        adapter = new StopListAdapter(this.getApplicationContext(),
                stopItems, color);
        stopList.setAdapter(adapter);
        stopList.setOnItemClickListener(new StopListClickListener());

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
                intent = new Intent(this, StopActivity.class);
                strings = new String [] {stops[position - 1],routeTag, stopTags[position - 1], color};
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


