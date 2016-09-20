package com.gmail.jasekurasz.bestbefore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ItemList extends Activity{

    dbTools db = new dbTools(this);
    private Button clearBtn;
    ListView listView;
    ItemCursorAdapter itemCursorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        clearBtn = (Button) findViewById(R.id.clearList);

        listView = (ListView) findViewById(R.id.itemsList);

        itemCursorAdapter = new ItemCursorAdapter(this, db.getAllItemsCursor(), 0);

        listView.setAdapter(itemCursorAdapter);

    }

    public void clearList(View view) {
        db.deleteAllItems();
        itemCursorAdapter.changeCursor(db.getAllItemsCursor());
        itemCursorAdapter.notifyDataSetChanged();
    }
}
