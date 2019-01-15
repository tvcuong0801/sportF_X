package com.example.tvcuo.sportf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Dat_Truoc_Activity extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat__truoc);
        listView=findViewById(R.id.list_Dat_truoc);
    }
}
