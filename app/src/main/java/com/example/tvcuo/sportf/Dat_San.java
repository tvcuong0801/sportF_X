package com.example.tvcuo.sportf;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

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
CheckBox checkBoxChonSan, checkBoxChonNgay, checkBoxChonGio;
EditText editTextNgay, editTextGio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_san);
        spinner=findViewById(R.id.spinner1);
        editTextNgay=findViewById(R.id.editTextNgay);
        editTextGio=findViewById(R.id.editTextGio);
        checkBoxChonSan=findViewById(R.id.checkBoxChonSan);
        checkBoxChonSan.setVisibility(View.INVISIBLE);
        checkBoxChonNgay=findViewById(R.id.checkBoxChonNgay);
        checkBoxChonGio=findViewById(R.id.checkBoxChonGio);
        checkBoxChonNgay.setVisibility(View.INVISIBLE);
        checkBoxChonGio.setVisibility(View.INVISIBLE);

        arrayListLoaiSan= new ArrayList<>();
        arrayListLoaiSan.add("Sân 20 người");
        arrayListLoaiSan.add("Sân 20 người có khán đài");
        arrayListLoaiSan.add("Sân 15 người");

        final ArrayAdapter<String> adapter= new ArrayAdapter<>(Dat_San.this,
                R.layout.support_simple_spinner_dropdown_item,
                arrayListLoaiSan);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chonSan= arrayListLoaiSan.get(position);
                checkBoxChonSan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            }
        },Nam,Thang,Ngay);
        datePickerDialog.show();
    }
}
