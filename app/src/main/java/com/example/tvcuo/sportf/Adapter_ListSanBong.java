package com.example.tvcuo.sportf;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_ListSanBong extends BaseAdapter {
    private Context context;
    private int layout;
    private List<SanBong> sanBongList;

    public Adapter_ListSanBong(Context context, int layout, List<SanBong> sanBongList) {
        this.context = context;
        this.layout = layout;
        this.sanBongList = sanBongList;
    }

    @Override
    public int getCount() {
        return sanBongList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewTen, textViewDiaChi, textViewLoai, textViewDanhGia;
        ImageView hinhAnh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.textViewTen=convertView.findViewById(R.id.textViewTen);
            holder.textViewDiaChi=convertView.findViewById(R.id.textViewDiaChi);
            holder.textViewLoai=convertView.findViewById(R.id.textViewLoai);
            holder.textViewDanhGia=convertView.findViewById(R.id.textViewDanhGia);
            holder.hinhAnh=convertView.findViewById(R.id.imageViewItemSanBong);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        SanBong sanBong= sanBongList.get(position);
        holder.textViewTen.setText(sanBong.getTen());
        holder.textViewDiaChi.setText(sanBong.getDiaChi());
        int loai= sanBong.getLoai();
        String strLoai="";
        switch (loai){
            case 1:
            {
                strLoai= "Sân bóng đá, sân cỏ nhân tạo";
                break;
            }
            case 2:
            {
                strLoai= "Sân tenis";
                break;
            }
            case 3:
            {
                strLoai= "Sân bóng chuyền";
                break;
            }
            case 4:
            {
                strLoai= "Sân bóng rổ";
                break;
            }
        }
        holder.textViewLoai.setText(strLoai);
        String danhGia= Double.toString(sanBong.getDanhGia())+ "Sao";
        holder.textViewDanhGia.setText(danhGia);
        String url= sanBong.getHinhAnh();
        Picasso.get()
                .load(url)
                .resize(100, 100)
                .centerCrop()
                .into(holder.hinhAnh);

        return convertView;
    }
}
