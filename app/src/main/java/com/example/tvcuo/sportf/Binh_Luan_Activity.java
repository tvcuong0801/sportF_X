package com.example.tvcuo.sportf;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Binh_Luan_Activity extends AppCompatActivity {
    ListView listView;
    ImageButton imageButton;
    EditText editText, editTextDanhGia;
    ArrayList<BinhLuan> binhLuanArrayList;
    Adapter_BinhLuan adapter_binhLuan;
    int idSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binh__luan_);
        listView = findViewById(R.id.listView_BinhLuan_bl);
        imageButton = findViewById(R.id.imageButtonBinhLuan_bl);
        editText = findViewById(R.id.editTextBinhLuan_bl);
        editTextDanhGia = findViewById(R.id.editTextDanhGia_bl);
        binhLuanArrayList = new ArrayList<>();
        idSB = SharedPreferencesManager.getIdSB_Hinh_Anh();
        adapter_binhLuan = new Adapter_BinhLuan(this, R.layout.item_binhluan, binhLuanArrayList);
        listView.setAdapter(adapter_binhLuan);
        loadCMT();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    if (editTextDanhGia.getText().toString().equals(""))
                        Toast.makeText(Binh_Luan_Activity.this, "Bạn chưa nhập bình luận", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.InsertCMT(idSB, editText.getText().toString(), Integer.parseInt(editTextDanhGia.getText().toString()));
                    binhLuanArrayList.clear();
                    loadCMT();
                }

            }
        });
    }

    private void loadCMT() {
        Cursor cursorCmt = MainActivity.dataBaseSanBong.getDataSql("SELECT * FROM BinhLuan WHERE IdSB = " + idSB);
        while (cursorCmt.moveToNext()) {
            binhLuanArrayList.add(new BinhLuan(cursorCmt.getInt(0),
                    cursorCmt.getInt(1),
                    cursorCmt.getString(2),
                    cursorCmt.getString(3),
                    cursorCmt.getString(4),
                    cursorCmt.getInt(5)));
        }
        adapter_binhLuan.notifyDataSetChanged();
    }
}
