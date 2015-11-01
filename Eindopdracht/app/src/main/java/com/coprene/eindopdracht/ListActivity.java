package com.coprene.eindopdracht;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    //Adapter and ArrayList
    private ArrayAdapter<String> adapter;
    private List<String> items;

    //Views
    private ListView listView;
    private EditText addItemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the ListView Layout as the Activity content
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the View
        listView = (ListView) findViewById(R.id.listView);
        addItemEditText = (EditText) findViewById(R.id.editText);

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        //Get the clicked item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //Get the name of the clicked item
        String clickedItem = (String) listView.getItemAtPosition(info.position);

        //Inflate the context menu from the resource file
        getMenuInflater().inflate(R.menu.context_menu, menu);

        //Find the delete MenuItem by its ID
        MenuItem deleteButton = menu.findItem(R.id.context_menu_delete_item);

        //Get the title from the menu button
        String originalTitle = deleteButton.getTitle().toString();

        //Make a new title combining the original title and the name of the clicked list item
        deleteButton.setTitle(originalTitle + " '" + clickedItem + "'?");

        //Let Android do its magic
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Retrieve info about the long pressed list item
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.context_menu_delete_item) {
            //Remove the item from the list
            items.remove(itemInfo.position);

            //Update the adapter to reflect the list change
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void addListItem(View view) {
        //Get the user text from the textfield
        String text = addItemEditText.getText().toString();

        //Check if some text has been added
        if (!(TextUtils.isEmpty(text))) {
            //Add the text to the adapter
            items.add(text);

            //Notify the adapter that the action_bar_menu of data has changed and the view should be updated
            adapter.notifyDataSetChanged();

            //Clear the EditText for the next item
            addItemEditText.setText("");
        } else {
            //Show a message to the user if the textfield is empty
            Toast.makeText(ListActivity.this, "Please enter some text in the textfield", Toast.LENGTH_LONG).show();
        }
    }
}
