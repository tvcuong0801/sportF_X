package com.example.tvcuo.sportf;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Dat_San extends AppCompatActivity {
    public static DonDatTruoc donDatTruoc;
    public static ArrayList<DonDatTruoc> donDatTruocArrayList;

    Spinner spinner;
    ArrayList<String> arrayListLoaiSan;
    ArrayList<SanCon> sanConArrayList;
    ArrayList<Integer> giaSanArrayList;
    String chonSan;
    String email;
    String ngayChon;
    String gioChon;
    String soGio;
    int tongTien = 120000;
    int tongTien1 = 1;
    ImageView imageViewHinhAnh;
    RadioButton radioButtonTaiCho, radioButtonTrucTuyen;
    CheckBox checkBoxChonSan, checkBoxChonNgay, checkBoxChonGio, checkBoxSoGio;
    EditText editTextGhiChu, editTextSoGio;
    DataBaseSanBong dataBaseSanBong;
    int idSB;
    TextView textViewten;
    TextView textViewGiaSan;
    TextView textViewngay, editTextNgay, editTextGio;
    TextView textViewgio;
    TextView textViewtongtien;
    TextView textViewloaiSan;
    TextView textViewSoGio;
    Button buttonXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_san);
        innitView();
        donDatTruocArrayList = new ArrayList<>();
        sanConArrayList = new ArrayList<>();
        giaSanArrayList = new ArrayList<>();
        email = SharedPreferencesManager.getEmail();
        idSB = SharedPreferencesManager.getIdSB_Hinh_Anh();

        Cursor cursor = dataBaseSanBong.getDataSql("SELECT * FROM SanBong WHERE Id = " + idSB);
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

        Cursor cursor1 = dataBaseSanBong.getDataSql("SELECT * FROM DonDatTruoc");
        while (cursor1.moveToNext()) {
            donDatTruocArrayList.add(new DonDatTruoc(
                    cursor1.getInt(0),
                    cursor1.getString(1),
                    cursor1.getInt(2),
                    cursor1.getString(3),
                    cursor1.getString(4),
                    cursor1.getString(5),
                    cursor1.getString(6),
                    cursor1.getString(7),
                    cursor1.getInt(8),
                    cursor1.getInt(9)
            ));
        }

        Cursor cursor2 = dataBaseSanBong.getDataSql("SELECT * FROM SanCon1 WHERE idSB = " + idSB);
        while (cursor2.moveToNext()) {
            sanConArrayList.add(new SanCon(
                    cursor2.getInt(0),
                    cursor2.getInt(1),
                    cursor2.getString(2),
                    cursor2.getInt(3)

            ));
        }

        assert sanBong != null;
        String url = sanBong.getHinhAnh();
        Picasso.get().load(url).into(imageViewHinhAnh);

        textViewten.setText(sanBong.getTen());

        arrayListLoaiSan = new ArrayList<>();

        for(int i = 0; i< sanConArrayList.size(); i++){
            arrayListLoaiSan.add(sanConArrayList.get(i).getTen());
            giaSanArrayList.add(sanConArrayList.get(i).getGia());
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Dat_San.this,
                R.layout.support_simple_spinner_dropdown_item,
                arrayListLoaiSan);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        control();


    }

    private void control() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chonSan = arrayListLoaiSan.get(position);
                checkBoxChonSan.setVisibility(View.VISIBLE);
                textViewloaiSan.setText("Loại sân: " + chonSan);
                tongTien1 = giaSanArrayList.get(position);
                textViewGiaSan.setText(String.valueOf(tongTien1));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        editTextSoGio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                checkBoxSoGio.setVisibility(View.VISIBLE);
                textViewSoGio.setText(editTextSoGio.getText().toString());
                soGio = editTextSoGio.getText().toString();
                if (soGio.equals("")) {
                    soGio = "1";
                } else
                    tongTien = tongTien1;
                textViewtongtien.setText(Integer.toString(tongTien * Integer.parseInt(soGio)));
                if (textViewtongtien.getText().toString().equals("Tổng tiền")) {
                    tongTien = 120000;
                } else tongTien = Integer.parseInt(textViewtongtien.getText().toString());
            }
        });

        imageViewHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Dat_San.this, Xem_Hinh_Activity.class);
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

        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewSoGio.getText().toString().equals("Số giờ") || textViewngay.getText().toString().equals("Ngày") || textViewgio.getText().toString().equals("Giờ")) {
                    dialogBaoTrung1();
                } else {
                    if (radioButtonTrucTuyen.isChecked()) {
                        ArrayList<DonDatTruoc> donDatTruocArrayList1;
                        donDatTruocArrayList1 = new ArrayList<>();
                        Cursor cursor1 = dataBaseSanBong.getDataSql("SELECT * FROM DonDatTruoc");
                        while (cursor1.moveToNext()) {
                            donDatTruocArrayList1.add(new DonDatTruoc(
                                    cursor1.getInt(0),
                                    cursor1.getString(1),
                                    cursor1.getInt(2),
                                    cursor1.getString(3),
                                    cursor1.getString(4),
                                    cursor1.getString(5),
                                    cursor1.getString(6),
                                    cursor1.getString(7),
                                    cursor1.getInt(8),
                                    cursor1.getInt(9)
                            ));
                        }
                        for (int i = 0; i < donDatTruocArrayList1.size(); i++) {
                            if (donDatTruocArrayList1.get(i).getGio().equals(textViewgio.getText().toString())
                                    && donDatTruocArrayList1.get(i).getNgay().equals(textViewngay.getText().toString())
                                    && donDatTruocArrayList1.get(i).getIdSB() == idSB && donDatTruocArrayList1.get(i).getLoaiSan().equals(chonSan)) {
                                Toast.makeText(Dat_San.this, "Đã trùng ", Toast.LENGTH_LONG).show();
                                dialogBaoTrung();
                                return;
                            }
                        }
                        startActivity(new Intent(Dat_San.this, Thanh_Toan_Activity.class));
                        donDatTruoc = new DonDatTruoc(donDatTruocArrayList.size() + 1, email, idSB, chonSan, ngayChon, gioChon, soGio, editTextGhiChu.getText().toString(), 1, tongTien);

                    } else if (radioButtonTaiCho.isChecked()) {

                        ArrayList<DonDatTruoc> donDatTruocArrayList1;
                        donDatTruocArrayList1 = new ArrayList<>();
                        Cursor cursor1 = dataBaseSanBong.getDataSql("SELECT * FROM DonDatTruoc");
                        while (cursor1.moveToNext()) {
                            donDatTruocArrayList1.add(new DonDatTruoc(
                                    cursor1.getInt(0),
                                    cursor1.getString(1),
                                    cursor1.getInt(2),
                                    cursor1.getString(3),
                                    cursor1.getString(4),
                                    cursor1.getString(5),
                                    cursor1.getString(6),
                                    cursor1.getString(7),
                                    cursor1.getInt(8),
                                    cursor1.getInt(9)
                            ));
                        }
                        for (int i = 0; i < donDatTruocArrayList1.size(); i++) {
                            if (donDatTruocArrayList1.get(i).getGio().equals(textViewgio.getText().toString())
                                    && donDatTruocArrayList1.get(i).getNgay().equals(textViewngay.getText().toString())
                                    && donDatTruocArrayList1.get(i).getIdSB() == idSB && donDatTruocArrayList1.get(i).getLoaiSan().equals(chonSan)) {
                                Toast.makeText(Dat_San.this, "Đã trùng ", Toast.LENGTH_LONG).show();
                                dialogBaoTrung();
                                return;
                            }
                        }
                        dialogXacNhanTraTaiCho();
                    }
                }


            }
        });


        radioButtonTaiCho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonTrucTuyen.isChecked() && radioButtonTaiCho.isChecked()) {
                    radioButtonTrucTuyen.setChecked(false);
                }
            }
        });
        radioButtonTrucTuyen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonTaiCho.isChecked() && radioButtonTrucTuyen.isChecked()) {
                    radioButtonTaiCho.setChecked(false);
                }
            }
        });
    }

    public void dialogBaoTrung() {
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Sân đặt trùng , xin mời đặt lại!");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Dat_San.this, MainActivity.class));
            }
        });
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Dat_San.this, XemSan_Activity.class);
                intent.putExtra("id", idSB);
                startActivity(intent);
            }
        });
        dialogXoa.show();
    }

    public void dialogBaoLoiGio() {
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Giờ bạn chọn phải sau giờ hiện tại và trước 21h00, xin mời đặt lại!");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Dat_San.this, MainActivity.class));
            }
        });
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Dat_San.this, XemSan_Activity.class);
                intent.putExtra("id", idSB);
                startActivity(intent);
            }
        });
        dialogXoa.show();
    }

    public void dialogBaoTrung1() {
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn chưa điền đủ thông tin , xin mời đặt lại!");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Dat_San.this, MainActivity.class));
            }
        });
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Dat_San.this, XemSan_Activity.class);
                intent.putExtra("id", idSB);
                startActivity(intent);
            }
        });
        dialogXoa.show();
    }

    public void dialogXacNhanTraTaiCho() {
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Số tiền tạm tính của bạn là: " + tongTien + ". Đây là số tiền tạm tính chưa  bao gồm tiền dịch vụ và tiền thêm giờ. Bạn phải có mặt tại sân trước ít nhất 10  phút. Nếu không đơn đặt trước sẽ bị hủy!");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Dat_San.this, MainActivity.class));
            }
        });

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.insertDatTruoc(email, idSB, chonSan, ngayChon, gioChon, soGio, editTextGhiChu.getText().toString(), 0, tongTien);
                Intent intent = new Intent(Dat_San.this, Dat_Truoc_Activity.class);
                startActivity(intent);
                Toast.makeText(Dat_San.this, "xong", Toast.LENGTH_LONG).show();
            }
        });
        dialogXoa.show();
    }

    private void innitView() {
        spinner = findViewById(R.id.spinner1);
        editTextNgay = findViewById(R.id.editTextNgay);
        editTextGio = findViewById(R.id.editTextGio);
        checkBoxChonSan = findViewById(R.id.checkBoxChonSan);
        checkBoxChonNgay = findViewById(R.id.checkBoxChonNgay);
        checkBoxChonGio = findViewById(R.id.checkBoxChonGio);
        radioButtonTaiCho = findViewById(R.id.radioButtonTaiCho);
        radioButtonTrucTuyen = findViewById(R.id.radioButtonTrucTuyen);
        editTextGhiChu = findViewById(R.id.editTextGhiChu);
        dataBaseSanBong = MainActivity.dataBaseSanBong;
        textViewten = findViewById(R.id.textView_Ten_DatSan);
        textViewngay = findViewById(R.id.textViewNgay_datsan);
        textViewgio = findViewById(R.id.textViewGio_datsan);
        textViewtongtien = findViewById(R.id.textViewTongTien_DatSan);
        textViewGiaSan = findViewById(R.id.textViewGiaSan);
        textViewloaiSan = findViewById(R.id.textView_Loaisan_datsan);
        imageViewHinhAnh = findViewById(R.id.imageView_DatSan_HinhAnh);
        checkBoxSoGio = findViewById(R.id.checkBoxSogio);
        editTextSoGio = findViewById(R.id.editTextSoGio);
        textViewSoGio = findViewById(R.id.textViewSoGio_dt);
        buttonXacNhan = findViewById(R.id.buttonXacNhan);

        checkBoxChonNgay.setVisibility(View.INVISIBLE);
        checkBoxChonGio.setVisibility(View.INVISIBLE);
        checkBoxChonSan.setVisibility(View.INVISIBLE);
        checkBoxSoGio.setVisibility(View.INVISIBLE);
    }

    private void chonGio() {
        final Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                editTextGio.setText(simpleDateFormat.format(calendar.getTime()));
                textViewgio.setText(simpleDateFormat.format(calendar.getTime()));
                gioChon = editTextGio.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = format.format(Calendar.getInstance().getTime());
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
                String currentTime = format2.format(Calendar.getInstance().getTime());
                String midNight = "21:00";
                Date date1 = null;
                Date giochon1 = null;
                Date midNight1 = null;
                try {
                    giochon1 = format2.parse(gioChon);
                    date1 = format2.parse(currentTime);
                    midNight1 = format2.parse(midNight);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if ((ngayChon.equals(currentDate) && giochon1.before(date1)) || giochon1.after(midNight1)) {
                    dialogBaoLoiGio();
                }
            }
        }, gio, phut, true);
        timePickerDialog.show();
    }

    private void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int Ngay = calendar.get(Calendar.DATE);
        int Thang = calendar.get(Calendar.MONTH);
        int Nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editTextNgay.setText(simpleDateFormat.format(calendar.getTime()));
                textViewngay.setText(simpleDateFormat.format(calendar.getTime()));
                ngayChon = editTextNgay.getText().toString();
            }
        }, Nam, Thang, Ngay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
