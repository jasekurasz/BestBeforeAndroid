package com.gmail.jasekurasz.bestbefore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by jasekurasz on 10/21/15.
 */
public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Context context1 = context;
        final String image;
        TextView itemName = (TextView) view.findViewById(R.id.itemName);
        TextView itemDate = (TextView) view.findViewById(R.id.itemDate);
        final ImageView itemPic = (ImageView) view.findViewById(R.id.itemPic);
        ImageView arrow = (ImageView) view.findViewById(R.id.arrowRight);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

        itemName.setText(name);
        itemDate.setText("Use by: " + date);
        itemPic.setImageURI(Uri.parse(image));

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, WholeImage.class);
                intent.putExtra("uri", image);
                context1.startActivity(intent);
            }
        });
    }

}
