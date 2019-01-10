package com.example.tvcuo.sportf;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Xem_Hinh_Activity extends AppCompatActivity {
DataBaseHinhAnh dataBaseHinhAnh;
GridView gridView;
int idSB;
ArrayList<HinhAnh> hinhAnhArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem__hinh);
        dataBaseHinhAnh= MainActivity.dataBaseHinhAnh;
        gridView=findViewById(R.id.gridViewHinhAnh);
        hinhAnhArrayList= new ArrayList<>();

        idSB=SharedPreferencesManager.getIdSB_Hinh_Anh();


        Adapter_HinhAnh adapter = new Adapter_HinhAnh(this, R.layout.item_hinh_anh, hinhAnhArrayList);
        gridView.setAdapter(adapter);
       // lấy data từ dataBase
        Cursor cursor=dataBaseHinhAnh.getDataSql("SELECT * FROM HinhAnh WHERE IdSB="+idSB);

        while (cursor.moveToNext()){
            hinhAnhArrayList.add(new HinhAnh(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2)
            ));
        }

        adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialogSua= new Dialog(Xem_Hinh_Activity.this);
                dialogSua.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogSua.setContentView(R.layout.dialog_xem_anh);
                ImageView imageView= dialogSua.findViewById(R.id.imageViewdialog);
                HinhAnh hinhAnh= hinhAnhArrayList.get(position);
                String url= hinhAnh.getUrl();
                Picasso.get()
                        .load(url)
                        .into(imageView);
                dialogSua.show();
            }
        });
    }

}
