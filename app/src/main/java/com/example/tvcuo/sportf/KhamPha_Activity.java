package com.example.tvcuo.sportf;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class KhamPha_Activity extends AppCompatActivity {
ListView listView;
DataBaseSanBong dataBaseSanBong;
ArrayList<SanBong> sanBongArrayList;
Adapter_ListSanBong adapter_listSanBong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kham_pha);
        listView = findViewById(R.id.list_KhamPha);
        dataBaseSanBong = MainActivity.dataBaseSanBong;
        sanBongArrayList = new ArrayList<>();

        adapter_listSanBong = new Adapter_ListSanBong(KhamPha_Activity.this, R.layout.iterm_list_sanbong,sanBongArrayList);
        listView.setAdapter(adapter_listSanBong);
        Cursor cursor = dataBaseSanBong.getDataSql("SELECT * FROM SanBong");
        while (cursor.moveToNext()){
            sanBongArrayList.add(new SanBong(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getString(5)
            ));

        }

        adapter_listSanBong.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanBong sanBong = sanBongArrayList.get(position);
                int idSB = sanBong.getIdSB();
                Intent intent = new Intent(KhamPha_Activity.this,XemSan_Activity.class);
                intent.putExtra("id",idSB);
                startActivity(intent);
            }
        });
    }
}
