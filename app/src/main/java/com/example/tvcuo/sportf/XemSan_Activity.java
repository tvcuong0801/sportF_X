package com.example.tvcuo.sportf;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class XemSan_Activity extends AppCompatActivity {
DataBaseSanBong dataBaseSanBong;
ImageView imageViewHinhAnh;
Button btnLienHe, btnDatSan;
int idSB;
ImageView imageViewChiDuong;
TextView textViewTen_xs, textViewDiaChi_xs, textViewLoai_xs;
ListView listViewCmt;
ArrayList<BinhLuan> binhLuanArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_san);
        final Intent intent = getIntent();
        idSB = intent.getIntExtra("id",0);

        dataBaseSanBong = MainActivity.dataBaseSanBong;
        textViewDiaChi_xs = findViewById(R.id.textView_xs_DiaChi);
        textViewTen_xs = findViewById(R.id.textView_xs_Ten);
        textViewLoai_xs = findViewById(R.id.textView_xs_Loai);
        imageViewHinhAnh = findViewById(R.id.imageView_DatSan_HinhAnh);
        imageViewChiDuong = findViewById(R.id.imageViewChiDuong);
        btnLienHe = findViewById(R.id.buttonGoi_dt);
        listViewCmt = findViewById(R.id.list_Binh_Luan);

        binhLuanArrayList = new ArrayList<>();
        btnDatSan = findViewById(R.id.buttonDatSan);
        SharedPreferencesManager.setIdSB_Hinh_Anh(idSB);
        Cursor cursorCmt = dataBaseSanBong.getDataSql("SELECT * FROM BinhLuan WHERE IdSB = "+ idSB);
        Adapter_BinhLuan adapter_binhLuan = new Adapter_BinhLuan(this, R.layout.item_binhluan, binhLuanArrayList);
        listViewCmt.setAdapter(adapter_binhLuan);

        while (cursorCmt.moveToNext()){
            binhLuanArrayList.add(new BinhLuan(cursorCmt.getInt(0),
                    cursorCmt.getInt(1),
                    cursorCmt.getString(2),
                    cursorCmt.getString(3),
                    cursorCmt.getString(4),
                    cursorCmt.getInt(5)));
        }
        adapter_binhLuan.notifyDataSetChanged();

        listViewCmt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!SharedPreferencesManager.isLogin()){
                    Toast.makeText(XemSan_Activity.this,"Bạn phải đăng nhập trước khi bình luận",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(XemSan_Activity.this,Dang_nhap.class));
                }
                else
                {
                    SharedPreferencesManager.setIdSB_Hinh_Anh(idSB);
                    startActivity(new Intent(XemSan_Activity.this,Binh_Luan_Activity.class));
                }
            }
        });

        Cursor cursor = dataBaseSanBong.getDataSql("SELECT * FROM SanBong WHERE Id = "+idSB);
        SanBong sanBong = null;
        while (cursor.moveToNext()) {
           sanBong = new SanBong(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                   cursor.getString(5));

        }
        assert sanBong != null;
        Toast.makeText(this, sanBong.getTen(), Toast.LENGTH_LONG).show();

       textViewTen_xs.setText(sanBong.getTen());
       textViewDiaChi_xs.setText(sanBong.getDiaChi());

        String strLoai = "";
        switch (sanBong.getLoai()){
            case 1:
            {
                strLoai = "Sân bóng đá, sân cỏ nhân tạo";
                break;
            }
            case 2:
            {
                strLoai = "Sân tenis";
                break;
            }
            case 3:
            {
                strLoai = "Sân bóng chuyền";
                break;
            }
            case 4:
            {
                strLoai = "Sân bóng rổ";
                break;
            }
        }

       textViewLoai_xs.setText(strLoai);
       String url = sanBong.getHinhAnh();
       Picasso.get().load(url).into(imageViewHinhAnh);

        final SanBong finalSanBong = sanBong;
        imageViewChiDuong.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //String labelMap = "Target";
               String geouri = "geo:0,0?q=10.848131, 106.786022("+ finalSanBong.getTen()+")";
               Intent mapInt = new Intent(Intent.ACTION_VIEW, Uri.parse(geouri));
               startActivity(mapInt);
           }
       });

        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+Uri.encode("0935808472")));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });

        imageViewHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(XemSan_Activity.this,Xem_Hinh_Activity.class);
                SharedPreferencesManager.init(XemSan_Activity.this);
                SharedPreferencesManager.setIdSB_Hinh_Anh(idSB);
                startActivity(intent1);
            }
        });

        btnDatSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SharedPreferencesManager.isLogin()){
                    Toast.makeText(XemSan_Activity.this,"Bạn phải đăng nhập trước khi đặt sân",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(XemSan_Activity.this, Dang_nhap.class));
                }
                else
                {
                    SharedPreferencesManager.setIdSB_Hinh_Anh(idSB);
                    startActivity(new Intent(XemSan_Activity.this, Dat_San.class));
                }


            }
        });

    }
}
