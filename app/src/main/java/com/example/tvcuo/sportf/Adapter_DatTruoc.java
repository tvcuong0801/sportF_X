package com.example.tvcuo.sportf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class Adapter_DatTruoc extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DonDatTruoc> datTruocList;

    Adapter_DatTruoc(Context context, int layout, List<DonDatTruoc> datTruocList) {
        this.context = context;
        this.layout = layout;
        this.datTruocList = datTruocList;
    }
    private class ViewHolder{
        TextView textViewTenSan, textViewLoaiSan, textViewNgayDat, textViewGioDat, textViewSoGioDat, textViewGhiChu, textViewTinhTrang;
    }

    @Override
    public int getCount() {
        return datTruocList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Adapter_DatTruoc.ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.textViewTenSan=convertView.findViewById(R.id.textViewTenSan_DT);
            holder.textViewGhiChu=convertView.findViewById(R.id.textViewGhiChu_dt);
            holder.textViewGioDat=convertView.findViewById(R.id.textViewGioDat_dt);
            holder.textViewSoGioDat=convertView.findViewById(R.id.textViewSoGio_dt);
            holder.textViewTinhTrang=convertView.findViewById(R.id.textViewTinhTrang_dt);
            holder.textViewLoaiSan=convertView.findViewById(R.id.textViewLoaSan_dt);
            holder.textViewNgayDat=convertView.findViewById(R.id.textViewNgayDat_dt);
            convertView.setTag(holder);
        }else {
            holder= (Adapter_DatTruoc.ViewHolder) convertView.getTag();
        }
        DonDatTruoc donDatTruoc= datTruocList.get(position);
        int idSB=donDatTruoc.getIdSB();
        String tenSB="";
        Cursor cursor= MainActivity.dataBaseSanBong.getDataSql("SELECT * FROM SanBong WHERE Id = "+ idSB);
        while (cursor.move(1))
        {
            SanBong sanBong= new SanBong(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getString(5));
            tenSB=sanBong.getTen();

        }
        holder.textViewTenSan.setText(tenSB);
        holder.textViewLoaiSan.setText("Loại sân: "+donDatTruoc.getLoaiSan());
        holder.textViewNgayDat.setText("Ngày đặt: "+ donDatTruoc.getNgay());
        holder.textViewGioDat.setText("Giờ đặt: "+donDatTruoc.getGio());
        holder.textViewSoGioDat.setText("Số giờ đặt: "+donDatTruoc.getSoGio());

        String ghiChu;
        if(donDatTruoc.ghiChu.equals("Ghi chú"))
        {
            ghiChu="Ghi chú:";
        }
        else ghiChu="Ghi chú: "+donDatTruoc.ghiChu;
        holder.textViewGhiChu.setText(ghiChu);
        int tinhTrang= donDatTruoc.getDaThanhToan();
        String strTinhTrang="";
        switch (tinhTrang){
            case 1:
            {
                strTinhTrang= "Đã thanh toán trực tuyến";
                break;
            }
            case 0:
            {
                strTinhTrang= "Thanh toán tại sân";
                break;
            }

        }
        holder.textViewTinhTrang.setText(strTinhTrang);
        return convertView;

    }
}
