package com.gmail.jasekurasz.bestbefore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class EnterNewItem extends AppCompatActivity {

    DatePicker datePicker;
    EditText itemName;
    ImageView itemPic;
    dbTools db = new dbTools(this);

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private String packagename = "com.gmail.jasekurasz.bestbefore";

    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null)
            actionbar.setTitle("New Food");
        setContentView(R.layout.activity_enter_new_item);
        saveBtn = (Button) findViewById(R.id.saveButton);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        itemName = (EditText) findViewById(R.id.itemName);
        itemPic = (ImageView) findViewById(R.id.itemPic);
        setButtonListeners();
    }

    private void setButtonListeners() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = Integer.toString(datePicker.getMonth());
                String day = Integer.toString(datePicker.getDayOfMonth());
                String year = Integer.toString(datePicker.getYear());
                HashMap<String, String> queryValuesMap = new HashMap<>();
                queryValuesMap.put(dbTools.dbEntry.COLUMN_NAME_FOOD_NAME, itemName.getText().toString());
                queryValuesMap.put(dbTools.dbEntry.COLUMN_NAME_DATE, day + "/" + month + "/" + year);
                if(fileUri == null) {
                    fileUri = Uri.parse("android.resource://"+packagename+"/drawable/no_img");
                    itemPic.setImageURI(fileUri);
                }
                queryValuesMap.put(dbTools.dbEntry.COLUMN_NAME_IMAGE, fileUri.toString());
                db.addItem(queryValuesMap);
                Intent intent = new Intent(v.getContext(), ItemList.class);
                startActivity(intent);
            }
        });
    }

    public void openCamera(View view) {
        //TODO open the camera and set the image to the imageview on this view
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "BestBefore");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("BestBefore", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                itemPic.setImageURI(fileUri);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
}
