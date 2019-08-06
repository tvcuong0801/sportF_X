package com.example.tvcuo.sportf;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static DataBaseSanBong dataBaseSanBong;
    public static DataBaseHinhAnh dataBaseHinhAnh;
    public static ArrayList<SanBong> sanBongArrayList;
    ImageButton btnKhamPha, btnDatTruoc, btnTintuc;
    ImageButton imageButtonDangNhap;
    ListView listView;

    public static void insertDatTruoc(String email, int idSB, String chonSan, String ngayChon, String gioChon, String soGio, String ghiChu, int daThanhToan, int tongTien) {
        dataBaseSanBong.InsertDataDatTruoc(email, idSB, chonSan, ngayChon, gioChon, soGio, ghiChu, daThanhToan, tongTien);
    }

    public static void InsertCMT(int idSB, String s, int i) {
        dataBaseSanBong.InsertBinhLuan(idSB, s, "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", SharedPreferencesManager.getTenFB(), i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // tạo file
        dataBaseSanBong = new DataBaseSanBong(this, "sanbong.sqlite", null, 1);
        //tạo bảng
        dataBaseSanBong.queryData("CREATE TABLE IF NOT EXISTS SanBong(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten NVARCHAR(250), DiaChi NVARCHAR(250), Loai INTEGER(2), DanhGia DOUBLE(2), HinhAnh NVARCHAR(300))");
        dataBaseSanBong.queryData("CREATE TABLE IF NOT EXISTS BinhLuan(Idbl INTEGER PRIMARY KEY AUTOINCREMENT, IdSB LONG , Cmt NVARCHAR(300), HinhAnh NVARCHAR(500), Ten NVARCHAR (50), DanhGia LONG)");
        dataBaseSanBong.queryData("CREATE TABLE IF NOT EXISTS DonDatTruoc (IdDT INTEGER PRIMARY KEY AUTOINCREMENT, Email NVARCHAR(50), IdSB LONG(2), LoaiSan NVARCHAR(50), Ngay NVARCHAR(20), Gio NVARCHAR(20), SoGio NVARCHAR(2), GhiChu NVARCHAR(250), DaThanhToan INTEGER(2), TongTien INTEGER(2))");
        dataBaseHinhAnh = new DataBaseHinhAnh(this, "hinhanh.sqlite", null, 1);
        dataBaseHinhAnh.queryData("CREATE TABLE IF NOT EXISTS HinhAnh(IdHA INTEGER PRIMARY KEY AUTOINCREMENT, IdSB LONG , Url NVARCHAR(300))");
        dataBaseSanBong.queryData("UPDATE SanBong SET HinhAnh ='" + "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg" + "' WHERE Ten = '" + "Basket" + "'");
        checkFirstTime();
        initView();
        control();
    }


    // kiểm tra đã có phải lần đầu tiên hay
    private void checkFirstTime() {
        SharedPreferencesManager.init(this);
        if (SharedPreferencesManager.isFirstTimeSetup()) {
            insertDataBaseSanBong();
            insertDataBaseHinhAnh();
            //insertDataDatTruoc();
            insertDataBinhLuan();
            SharedPreferencesManager.setFirstTimeSetup(false);
        } else {
            Toast.makeText(this, "Chào mừng trở lại", Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        btnKhamPha = findViewById(R.id.btnkhampha);
        btnDatTruoc = findViewById(R.id.btndattruoc);
        btnTintuc = findViewById(R.id.btntintuc);
        listView = findViewById(R.id.listSanbong);
        imageButtonDangNhap = findViewById(R.id.imageButtonDangNhap);

        sanBongArrayList = new ArrayList<>();
        Adapter_ListSanBong adapter_listSanBong = new Adapter_ListSanBong(this, R.layout.iterm_list_sanbong, sanBongArrayList);
        listView.setAdapter(adapter_listSanBong);
        // lấy data từ dataBase
        Cursor cursor = dataBaseSanBong.getDataSql("SELECT * FROM SanBong");
        while (cursor.moveToNext()) {
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


    }

    //1 3 6 8 11 13 16 18 21 23
    //2 7 12 17 22
    // 4 9 14 19 24
    // 5 10 15 20 25
    private void insertDataBaseHinhAnh() {
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(1, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(1, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(1, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(1, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(1, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(1, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(1, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(1, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(1, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(1, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(1, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(1, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(1, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(1, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(1, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(1, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(1, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(1, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(1, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(3, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(3, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(3, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(3, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(3, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(3, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(3, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(3, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(3, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(3, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(3, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(3, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(3, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(3, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(3, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(3, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(3, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(3, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(3, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(6, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(6, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(6, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(6, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(6, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(6, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(6, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(6, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(6, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(6, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(6, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(6, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(6, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(6, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(6, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(6, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(6, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(6, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(6, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(8, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(8, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(8, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(8, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(8, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(8, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(8, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(8, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(8, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(8, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(8, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(8, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(8, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(8, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(8, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(8, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(8, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(8, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(8, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(11, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(11, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(11, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(11, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(11, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(11, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(11, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(11, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(11, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(11, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(11, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(11, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(11, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(11, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(11, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(11, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(11, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(11, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(11, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(13, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(13, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(13, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(13, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(13, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(13, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(13, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(13, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(13, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(13, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(13, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(13, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(13, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(13, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(13, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(13, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(13, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(13, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(13, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(16, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(16, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(16, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(16, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(16, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(16, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(16, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(16, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(16, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(16, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(16, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(16, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(16, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(16, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(16, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(16, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(16, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(16, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(16, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(18, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(18, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(18, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(18, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(18, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(18, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(18, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(18, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(18, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(18, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(18, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(18, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(18, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(18, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(18, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(18, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(18, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(18, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(18, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(21, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(21, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(21, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(21, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(21, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(21, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(21, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(21, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(21, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(21, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(21, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(21, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(21, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(21, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(21, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(21, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(21, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(21, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(21, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(23, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(23, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(23, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(23, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(23, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(23, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(23, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(23, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(23, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");
        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g17/164586/prof/s576x330/foody-mobile-hmb-h-jpg-284-635818230095362508.jpg");
        dataBaseHinhAnh.Insert(23, "http://www.sanconhantao.com/wp-content/uploads/2016/09/dich-vu-cung-cap-co-nhan-tao-san-bong-da-chat-luong-cao.jpg");
        dataBaseHinhAnh.Insert(23, "https://sanchoi.com.vn/wp-content/uploads/2016/10/san-bong-co-nhan-tao-cong-vien-cac-hoang-tu-1.jpg");
        dataBaseHinhAnh.Insert(23, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/su-dung-co-nhan-tao-cho-san-bong-360x250.jpg");
        dataBaseHinhAnh.Insert(23, "https://conhantaonguyengia.com/uploads/2015/03/Co-nhan-tao-san-bong-Nguyen-Gia.jpg");
        dataBaseHinhAnh.Insert(23, "https://i.ytimg.com/vi/MeuwT1QxeVU/maxresdefault.jpg");
        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g22/217850/prof/s576x330/foody-mobile-m-bong-jpg-625-635947759420769298.jpg");
        dataBaseHinhAnh.Insert(23, "http://sanconhantao.com.vn/wp-content/uploads/2018/01/san-bong-mini.jpg");
        dataBaseHinhAnh.Insert(23, "http://image.baobinhduong.vn/news/2015/20150616/fckimage/HAGL.jpg");
        dataBaseHinhAnh.Insert(23, "https://conhantaothanhthinh.com/wp-content/uploads/2017/09/Khu-s%C3%A2n-c%E1%BB%8F-nh%C3%A2n-t%E1%BA%A1o-Thi%C3%AAn-T%E1%BB%A9-C%C3%A0-Mau.jpg");
        dataBaseHinhAnh.Insert(23, "http://imgs.baobacgiang.com.vn/2018/05/04/09/20180504094242-11a1.jpg");
        dataBaseHinhAnh.Insert(23, "https://images.foody.vn/res/g23/223279/prof/s576x330/foody-mobile-m-moi-jpg-612-635948653966211268.jpg");

        dataBaseHinhAnh.Insert(2, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(2, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(2, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(2, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(2, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(7, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(7, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(7, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(7, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(7, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(12, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(12, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(12, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(12, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(12, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(17, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(17, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(17, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(17, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(17, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(22, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(22, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(22, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(22, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(22, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(4, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(4, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(4, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(4, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(4, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(9, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(9, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(9, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(9, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(9, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(14, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(14, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(14, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(14, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(14, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(19, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(19, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(19, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(19, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(19, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(24, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(24, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(24, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(24, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(24, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(5, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(5, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(5, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(5, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(5, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(10, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(10, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(10, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(10, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(10, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(15, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(15, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(15, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(15, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(15, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(20, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(20, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(20, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(20, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(20, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(25, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(25, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(25, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(25, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(25, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(2, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(2, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(2, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(2, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(2, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(7, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(7, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(7, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(7, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(7, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(12, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(12, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(12, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(12, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(12, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(17, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(17, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(17, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(17, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(17, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(22, "https://images.foody.vn/res/g14/137761/prof/s576x330/foody-mobile-1-jpg-568-635678037372042535.jpg");
        dataBaseHinhAnh.Insert(22, "https://placevietnam.com/img/600x337/san_pham/san-tennis-rach-mieu.jpg");
        dataBaseHinhAnh.Insert(22, "https://aplico.com.vn/assets/uploads/images/c%C3%B4ng%20tr%C3%ACnh%20chi%E1%BA%BFu%20s%C3%A1ng/san%20choi%20tennis.jpg");
        dataBaseHinhAnh.Insert(22, "https://images.foody.vn/res/g14/137649/prof/s576x330/foody-mobile-3-jpg-386-635677965756528749.jpg");
        dataBaseHinhAnh.Insert(22, "http://thegioitennis.com.vn/uploads/images/danh-sach-san-bong-tennis-khu-vuc-ha-noi-1.jpg");

        dataBaseHinhAnh.Insert(4, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(4, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(4, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(4, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(4, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(9, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(9, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(9, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(9, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(9, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(14, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(14, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(14, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(14, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(14, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(19, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(19, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(19, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(19, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(19, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(24, "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseHinhAnh.Insert(24, "https://sannhuagachnhua.com/image/cache/data/NEWS/san%20baminton-600x600.jpg");
        dataBaseHinhAnh.Insert(24, "http://www.myuc.vn/uploads/products/2017/01/04/bongchuyen.png");
        dataBaseHinhAnh.Insert(24, "http://www.sanbongro.com.vn/uploads/supply/2016/05/21/tham_bong_chuyen_70mm_pqbc_140.jpg");
        dataBaseHinhAnh.Insert(24, "http://sannhuagachnhua.com/image/data/NEWS/san%20the%20thao/san-cau-long/san-cau-long-01.jpg");

        dataBaseHinhAnh.Insert(5, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(5, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(5, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(5, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(5, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(10, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(10, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(10, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(10, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(10, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(15, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(15, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(15, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(15, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(15, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(20, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(20, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(20, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(20, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(20, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

        dataBaseHinhAnh.Insert(25, "http://asiansports.com.vn/wp-content/uploads/2017/03/thi-cong-san-bong-ro-dat-chuan-600x450.jpg");
        dataBaseHinhAnh.Insert(25, "http://asiansports.com.vn/wp-content/uploads/2017/07/IMG_20170619_210927-450x600.jpg");
        dataBaseHinhAnh.Insert(25, "http://thegioiconhantao.com.vn/wp-content/uploads/2016/11/co-nhan-tao-san-bong-ro-2.jpg");
        dataBaseHinhAnh.Insert(25, "http://www.myuc.vn/uploads/products/2016/05/16/20141111_145223_copy.jpg");
        dataBaseHinhAnh.Insert(25, "http://conhantaothanhthuong.com/assets/news/2017_02/san-bong-ro-2.jpg");

    }

    private void insertDataBinhLuan() {
        dataBaseSanBong.InsertBinhLuan(1, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(1, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(1, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-367-456319.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(1, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(2, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(2, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(2, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(2, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(3, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(3, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(3, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(3, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(4, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(4, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(4, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(4, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(5, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(5, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(5, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(5, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(6, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(6, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(6, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(6, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(7, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(7, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(7, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(7, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(8, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(8, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(8, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(8, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(9, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(9, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(9, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(9, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(10, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(10, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(10, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(10, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(11, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(11, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(11, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-367-456319.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(11, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(12, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(12, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(12, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(12, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(13, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(13, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(13, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(13, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(14, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(14, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(14, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(14, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(15, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(15, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(15, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(15, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(16, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(16, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(16, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(16, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(17, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(17, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(17, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(17, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(18, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(18, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(18, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(18, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(19, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(19, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(19, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(19, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(20, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(20, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(20, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(20, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(21, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(21, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(21, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(21, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(22, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(22, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(22, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(22, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(23, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(23, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(23, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(23, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(24, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(24, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(24, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(24, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

        dataBaseSanBong.InsertBinhLuan(25, "Sân mới, tốt, nhưng ít nhân viên.", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Đàm Vĩnh Hưng", 4);
        dataBaseSanBong.InsertBinhLuan(25, "GOOD, sân sạch sẽ", "https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png", "Hồ Ngọc Hà", 5);
        dataBaseSanBong.InsertBinhLuan(25, "Sẽ đến nhiều lần", "https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png", "Karik", 4);
        dataBaseSanBong.InsertBinhLuan(25, "Nhân viên dọa đánh mình", "https://png.pngtree.com/svg/20170602/avatar_107646.png", "Mỹ Linh", 3);

    }

    private void insertDataBaseSanBong() {
        dataBaseSanBong.Insert("Đại Châu", "99 Man Thiện, Hiệp Phú, Quận 9", 1, 4.5,
                "https://content.ibebiz.net/webs/1907759806/3934536624/images/content/2cec7cf6-c7f4-4dc2-a681-2cd8321d69fb-0.jpg");
        dataBaseSanBong.Insert("Nam Đông", "70 Bà Huyện Thanh Quan, Phường 7, Quận 3", 2, 3.5,
                "https://i.khoahoc.tv/photos/image/2018/05/30/san-tennis.jpg");
        dataBaseSanBong.Insert("Cao Thắng", "25 Cao Thắng, Phường 13, Quận 3", 1, 3.5,
                "https://bongda.choithethao.vn/wp-content/uploads/2016/11/s%C3%A2n-b%C3%B3ng-%C4%91%C3%A1-mini-chuy%C3%AAn-vi%E1%BB%87t-%C4%91%C3%A0-n%E1%BA%B5ng-656x365.jpg");
        dataBaseSanBong.Insert("Quân Việt", "1825 Võ Văn Kiệt, Phường 2, Quận 5", 3, 5,
                "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseSanBong.Insert("Basket", "93 Võ Văn Ngân, Linh Trung, Thủ Đức", 4, 2.5,
                "https://i.ytimg.com/vi/3d9d9WRuPFE/maxresdefault.jpg");
        dataBaseSanBong.Insert("Đại Châu", "99 Man Thiện, Hiệp Phú, Quận 9", 1, 4.5,
                "https://content.ibebiz.net/webs/1907759806/3934536624/images/content/2cec7cf6-c7f4-4dc2-a681-2cd8321d69fb-0.jpg");
        dataBaseSanBong.Insert("Nam Đông", "70 Bà Huyện Thanh Quan, Phường 7, Quận 3", 2, 3.5,
                "https://i.khoahoc.tv/photos/image/2018/05/30/san-tennis.jpg");
        dataBaseSanBong.Insert("Cao Thắng", "25 Cao Thắng, Phường 13, Quận 3", 1, 3.5,
                "https://bongda.choithethao.vn/wp-content/uploads/2016/11/s%C3%A2n-b%C3%B3ng-%C4%91%C3%A1-mini-chuy%C3%AAn-vi%E1%BB%87t-%C4%91%C3%A0-n%E1%BA%B5ng-656x365.jpg");
        dataBaseSanBong.Insert("Quân Việt", "1825 Võ Văn Kiệt, Phường 2, Quận 5", 3, 5,
                "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseSanBong.Insert("Basket", "93 Võ Văn Ngân, Linh Trung, Thủ Đức", 4, 2.5,
                "https://i.ytimg.com/vi/3d9d9WRuPFE/maxresdefault.jpg");
        dataBaseSanBong.Insert("Đại Châu", "99 Man Thiện, Hiệp Phú, Quận 9", 1, 4.5,
                "https://content.ibebiz.net/webs/1907759806/3934536624/images/content/2cec7cf6-c7f4-4dc2-a681-2cd8321d69fb-0.jpg");
        dataBaseSanBong.Insert("Nam Đông", "70 Bà Huyện Thanh Quan, Phường 7, Quận 3", 2, 3.5,
                "https://i.khoahoc.tv/photos/image/2018/05/30/san-tennis.jpg");
        dataBaseSanBong.Insert("Cao Thắng", "25 Cao Thắng, Phường 13, Quận 3", 1, 3.5,
                "https://bongda.choithethao.vn/wp-content/uploads/2016/11/s%C3%A2n-b%C3%B3ng-%C4%91%C3%A1-mini-chuy%C3%AAn-vi%E1%BB%87t-%C4%91%C3%A0-n%E1%BA%B5ng-656x365.jpg");
        dataBaseSanBong.Insert("Quân Việt", "1825 Võ Văn Kiệt, Phường 2, Quận 5", 3, 5,
                "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseSanBong.Insert("Basket", "93 Võ Văn Ngân, Linh Trung, Thủ Đức", 4, 2.5,
                "https://i.ytimg.com/vi/3d9d9WRuPFE/maxresdefault.jpg");
        dataBaseSanBong.Insert("Đại Châu", "99 Man Thiện, Hiệp Phú, Quận 9", 1, 4.5,
                "https://content.ibebiz.net/webs/1907759806/3934536624/images/content/2cec7cf6-c7f4-4dc2-a681-2cd8321d69fb-0.jpg");
        dataBaseSanBong.Insert("Nam Đông", "70 Bà Huyện Thanh Quan, Phường 7, Quận 3", 2, 3.5,
                "https://i.khoahoc.tv/photos/image/2018/05/30/san-tennis.jpg");
        dataBaseSanBong.Insert("Cao Thắng", "25 Cao Thắng, Phường 13, Quận 3", 1, 3.5,
                "https://bongda.choithethao.vn/wp-content/uploads/2016/11/s%C3%A2n-b%C3%B3ng-%C4%91%C3%A1-mini-chuy%C3%AAn-vi%E1%BB%87t-%C4%91%C3%A0-n%E1%BA%B5ng-656x365.jpg");
        dataBaseSanBong.Insert("Quân Việt", "1825 Võ Văn Kiệt, Phường 2, Quận 5", 3, 5,
                "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseSanBong.Insert("Basket", "93 Võ Văn Ngân, Linh Trung, Thủ Đức", 4, 2.5,
                "https://i.ytimg.com/vi/3d9d9WRuPFE/maxresdefault.jpg");
        dataBaseSanBong.Insert("Đại Châu", "99 Man Thiện, Hiệp Phú, Quận 9", 1, 4.5,
                "https://content.ibebiz.net/webs/1907759806/3934536624/images/content/2cec7cf6-c7f4-4dc2-a681-2cd8321d69fb-0.jpg");
        dataBaseSanBong.Insert("Nam Đông", "70 Bà Huyện Thanh Quan, Phường 7, Quận 3", 2, 3.5,
                "https://i.khoahoc.tv/photos/image/2018/05/30/san-tennis.jpg");
        dataBaseSanBong.Insert("Cao Thắng", "25 Cao Thắng, Phường 13, Quận 3", 1, 3.5,
                "https://bongda.choithethao.vn/wp-content/uploads/2016/11/s%C3%A2n-b%C3%B3ng-%C4%91%C3%A1-mini-chuy%C3%AAn-vi%E1%BB%87t-%C4%91%C3%A0-n%E1%BA%B5ng-656x365.jpg");
        dataBaseSanBong.Insert("Quân Việt", "1825 Võ Văn Kiệt, Phường 2, Quận 5", 3, 5,
                "http://suasantennis.com/uploads/albums/unnamed-5.jpg");
        dataBaseSanBong.Insert("Basket", "93 Võ Văn Ngân, Linh Trung, Thủ Đức", 4, 2.5,
                "https://i.ytimg.com/vi/3d9d9WRuPFE/maxresdefault.jpg");
    }
//    private void insertDataDatTruoc() {
//        dataBaseSanBong.InsertDataDatTruoc("nodoann@gmail.com", 1, "Sân 20 người", "17/01/2019", "14:30","1"," ", 1,120000); }


    private void control() {

        btnKhamPha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KhamPha_Activity.class);
                startActivity(intent);
            }
        });

        btnDatTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPreferencesManager.isLogin()) {
                    Toast.makeText(MainActivity.this, "Bạn phải đăng nhập trước khi đặt sân", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Dang_nhap.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Dat_Truoc_Activity.class));
                }
            }
        });

        btnTintuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tintuc_Activity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanBong sanBong = sanBongArrayList.get(position);
                int idSB = sanBong.getIdSB();
                Intent intent = new Intent(MainActivity.this, XemSan_Activity.class);
                intent.putExtra("id", idSB);
                startActivity(intent);
            }
        });

        imageButtonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Dang_nhap.class));
            }
        });

    }
}
