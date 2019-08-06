package com.example.tvcuo.sportf;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Dat_Truoc_Activity extends AppCompatActivity {
    ListView listView;
    Adapter_DatTruoc adapterDatTruoc;
    ArrayList<DonDatTruoc> donDatTruocArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat__truoc);
        listView = findViewById(R.id.list_Dat_truoc);
        donDatTruocArrayList = new ArrayList<>();
        adapterDatTruoc = new Adapter_DatTruoc(this, R.layout.item_dattruoc, donDatTruocArrayList);
        listView.setAdapter(adapterDatTruoc);
        Cursor cursor = MainActivity.dataBaseSanBong.getDataSql("SELECT * FROM DonDatTruoc WHERE Email = '" + SharedPreferencesManager.getEmail() + "'");
        while (cursor.moveToNext()) {
            donDatTruocArrayList.add(new DonDatTruoc(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            ));
        }
        adapterDatTruoc.notifyDataSetChanged();

    }
}
