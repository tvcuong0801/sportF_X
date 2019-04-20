package com.example.tvcuo.sportf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class Thanh_Toan_Activity extends AppCompatActivity {
    Button buttonThanhtoan;
    DonDatTruoc donDatTruoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh__toan_);
        buttonThanhtoan=findViewById(R.id.buttonThanhToan);
        buttonThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=getIntent();
                donDatTruoc=Dat_San.donDatTruoc;
                MainActivity.insertDatTruoc(
                        donDatTruoc.getEmail(),
                        donDatTruoc.getIdSB(),
                        donDatTruoc.getLoaiSan(),
                        donDatTruoc.getNgay(),
                        donDatTruoc.getGio(),
                        donDatTruoc.getSoGio(),
                        donDatTruoc.getGhiChu(),
                        donDatTruoc.getDaThanhToan(),
                        donDatTruoc.getTongTien());
                startActivity(new Intent(Thanh_Toan_Activity.this,Dat_Truoc_Activity.class));

            }
        });
    }
}
