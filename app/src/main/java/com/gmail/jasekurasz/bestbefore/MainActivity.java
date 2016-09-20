package com.gmail.jasekurasz.bestbefore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button newItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        setContentView(R.layout.activity_main);
        newItemBtn = (Button) findViewById(R.id.newItemBtn);
        setButtonListeners();
    }

    private void setButtonListeners() {
        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EnterNewItem.class);
                startActivity(intent);
            }
        });
    }

}
