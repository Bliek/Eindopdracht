package com.coprene.eindopdracht;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> items;

    //Declare the listView variable
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Set the ListView Layout as the Activity content
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the listView
        listView = (ListView) findViewById(R.id.listView);

        //Create the List of items
        items = new ArrayList<String>();

        //Create the Array Adapter, give it a layout and a list of values
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        //Add items to the Arraylist
        items.add("iPad2");
        items.add("iPhone5");
        items.add("iPhone6");
        adapter.notifyDataSetChanged();

        //Set the newly created adapter as the adapter for the listview
        listView.setAdapter(adapter);

        //Register the the ListView for a context menu
        registerForContextMenu(listView);

        //Set the listview on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View listItem, int position, long id) {
                //Get the value of the item that the user clicked on
                String clickedItem = (String) parent.getItemAtPosition(position);

                //Display a Toast message to show the user the item he/she clicked on
                Toast.makeText(ListActivity.this, "Clicked: " + clickedItem, Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Check which menu item has been clicked
        if (item.getItemId() == R.id.action_bar_menu_delete_all) {
            //Clears the list
            items.clear();
            //Tell the adapter that it should reload the data
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
