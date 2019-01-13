package com.example.tvcuo.sportf;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class Dat_San extends AppCompatActivity {
Spinner spinner;
ArrayList<String> arrayListLoaiSan;
String chonSan;
ImageView imageViewHinhAnh;
RadioButton radioButtonTaiCho, radioButtonTrucTuyen;
CheckBox checkBoxChonSan, checkBoxChonNgay, checkBoxChonGio, checkBoxSoGio;
EditText editTextNgay, editTextGio, editTextGhiChu, editTextSoGio;
DataBaseSanBong dataBaseSanBong;
int idSB;
TextView textViewten, textViewngay, textViewgio, textViewtongtien, textViewloaiSan, textViewSoGio;
Button buttonXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_san);
        innitView();




        idSB= SharedPreferencesManager.getIdSB_Hinh_Anh();

        Cursor cursor= dataBaseSanBong.getDataSql("SELECT * FROM SanBong WHERE Id = "+idSB);
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

        String url= sanBong.getHinhAnh();
        Picasso.get().load(url).into(imageViewHinhAnh);

        textViewten.setText(sanBong.getTen());

        arrayListLoaiSan= new ArrayList<>();

        switch (sanBong.getLoai()){
            case 1:
            {
                arrayListLoaiSan.add("Sân 20 người");
                arrayListLoaiSan.add("Sân 20 người có khán đài");
                arrayListLoaiSan.add("Sân 15 người");
                break;
            }
            case 2:
            {
                arrayListLoaiSan.add("Có khán đài");
                arrayListLoaiSan.add("Không khán đài");
                break;
            }
            case 3:
            {
                arrayListLoaiSan.add("Có khán đài");
                arrayListLoaiSan.add("Không khán đài");
                break;
            }
            case 4:
            {
                arrayListLoaiSan.add("Một rổ");
                arrayListLoaiSan.add("Hai rổ có khán đài");
                arrayListLoaiSan.add("Hai rổ không khán đài");
                break;
            }
        }

        final ArrayAdapter<String> adapter= new ArrayAdapter<>(Dat_San.this,
                R.layout.support_simple_spinner_dropdown_item,
                arrayListLoaiSan);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        control();

    }

    private void control() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chonSan= arrayListLoaiSan.get(position);
                checkBoxChonSan.setVisibility(View.VISIBLE);
                textViewloaiSan.setText("Loại sân: "+chonSan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editTextSoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxSoGio.setVisibility(View.VISIBLE);
                textViewSoGio.setText("Số giờ "+editTextSoGio.getText());
            }
        });

        imageViewHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Dat_San.this,Xem_Hinh_Activity.class);
                SharedPreferencesManager.init(Dat_San.this);
                SharedPreferencesManager.setIdSB_Hinh_Anh(idSB);
                startActivity(intent1);
            }
        });

        editTextNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
                checkBoxChonNgay.setVisibility(View.VISIBLE);
            }
        });

        editTextGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonGio();
                checkBoxChonGio.setVisibility(View.VISIBLE);
            }
        });


    }

    private void innitView() {
        spinner=findViewById(R.id.spinner1);
        editTextNgay=findViewById(R.id.editTextNgay);
        editTextGio=findViewById(R.id.editTextGio);
        checkBoxChonSan=findViewById(R.id.checkBoxChonSan);
        checkBoxChonNgay=findViewById(R.id.checkBoxChonNgay);
        checkBoxChonGio=findViewById(R.id.checkBoxChonGio);
        radioButtonTaiCho=findViewById(R.id.radioButtonTaiCho);
        radioButtonTrucTuyen=findViewById(R.id.radioButtonTrucTuyen);
        editTextGhiChu=findViewById(R.id.editTextGhiChu);
        dataBaseSanBong=MainActivity.dataBaseSanBong;
        textViewten=findViewById(R.id.textView_Ten_DatSan);
        textViewngay=findViewById(R.id.textViewNgay_datsan);
        textViewgio=findViewById(R.id.textViewGio_datsan);
        textViewtongtien=findViewById(R.id.textViewTongTien_DatSan);
        textViewloaiSan=findViewById(R.id.textView_Loaisan_datsan);
        imageViewHinhAnh=findViewById(R.id.imageView_DatSan_HinhAnh);
        checkBoxSoGio=findViewById(R.id.checkBoxSogio);
        editTextSoGio=findViewById(R.id.editTextSoGio);
        textViewSoGio=findViewById(R.id.textViewSoGio);
        buttonXacNhan=findViewById(R.id.buttonXacNhan);
        checkBoxChonNgay.setVisibility(View.INVISIBLE);
        checkBoxChonGio.setVisibility(View.INVISIBLE);
        checkBoxChonSan.setVisibility(View.INVISIBLE);
        checkBoxSoGio.setVisibility(View.INVISIBLE);
    }

    private void chonGio() {
        final Calendar calendar=Calendar.getInstance();
        int gio= calendar.get(Calendar.HOUR_OF_DAY);
        int phut=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,hourOfDay,minute);
                editTextGio.setText(simpleDateFormat.format(calendar.getTime()));
                textViewgio.setText("Giờ "+simpleDateFormat.format(calendar.getTime()));
            }
        },gio,phut,true);
        timePickerDialog.show();
    }

    private void chonNgay(){
        final Calendar calendar=Calendar.getInstance();
        int Ngay=calendar.get(Calendar.DATE);
        int Thang=calendar.get(Calendar.MONTH);
        int Nam=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                editTextNgay.setText(simpleDateFormat.format(calendar.getTime()));
                textViewngay.setText("Ngày "+simpleDateFormat.format(calendar.getTime()));
            }
        },Nam,Thang,Ngay);
        datePickerDialog.show();
    }
}
