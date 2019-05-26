package com.example.tvcuo.sportf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DataBaseSanBong extends SQLiteOpenHelper {
    DataBaseSanBong(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//không trả về, tạo, cập nhật, thêm sửa xóa
    void queryData(String sqlQuery){
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL(sqlQuery);
    }
// trả về dữ liệu con trỏ, dùng để lấy dữ liệu
    public Cursor getDataSql(String sqlQuery){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sqlQuery,null);
    }

    void InsertBinhLuan(int idsb, String cmt, String hinhanh, String ten, int danhGia){
        SQLiteDatabase database= getWritableDatabase();
        String sql="INSERT INTO BinhLuan VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,idsb);
        statement.bindString(2,cmt);
        statement.bindString(3, hinhanh);
        statement.bindString(4,ten);
        statement.bindLong(5,danhGia);
        statement.executeInsert();
    }


    void Insert(String ten, String diachi, int loai, double danhgia, String hinhAnh){
        SQLiteDatabase database= getWritableDatabase();
        String sql= "INSERT INTO SanBong VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindString(2,diachi);
        statement.bindLong(3,loai);
        statement.bindDouble(4,danhgia);
        statement.bindString(5,hinhAnh);
        statement.executeInsert();
    }

    void InsertDataDatTruoc(String email, int idSB, String loaiSan, String ngay, String gio, String soGio, String ghiChu, int daThanhToan, int tongTien)
    {
        SQLiteDatabase database= getWritableDatabase();
        String sql= "INSERT INTO DonDatTruoc VALUES(null, ?, ?, ?, ?, ?,?,?,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,email);
        statement.bindLong(2,idSB);
        statement.bindString(3,loaiSan);
        statement.bindString(4,ngay);
        statement.bindString(5,gio);
        statement.bindString(6,soGio);
        statement.bindString(7,ghiChu);
        statement.bindLong(8,daThanhToan);
        statement.bindLong(9,tongTien);
        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
