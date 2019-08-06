package com.example.tvcuo.sportf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_BinhLuan extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BinhLuan> listBinhLuan;

    Adapter_BinhLuan(Context context, int layout, List<BinhLuan> listBinhLuan) {
        this.context = context;
        this.layout = layout;
        this.listBinhLuan = listBinhLuan;
    }

    @Override
    public int getCount() {
        return listBinhLuan.size();
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

        Adapter_BinhLuan.ViewHolder holder;
        if (convertView == null) {
            holder = new Adapter_BinhLuan.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textViewTenCmt = convertView.findViewById(R.id.textViewTen_Cmt);
            holder.textViewDanhGiaCmt = convertView.findViewById(R.id.textView_danhgia_cmt);
            holder.textViewCmt = convertView.findViewById(R.id.textViewCmt);
            holder.hinhAnh = convertView.findViewById(R.id.imageViewHinhAnh_cmt);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_BinhLuan.ViewHolder) convertView.getTag();
        }
        BinhLuan binhLuan = listBinhLuan.get(position);
        holder.textViewTenCmt.setText(binhLuan.getTen());
        holder.textViewCmt.setText(binhLuan.getCmt());
        holder.textViewDanhGiaCmt.setText(Integer.toString(binhLuan.getDanhGia()) + " sao");
        String url = binhLuan.getHinhAnh();
        Picasso.get()
                .load(url)
                .resize(100, 100)
                .centerCrop()
                .into(holder.hinhAnh);
        return convertView;
    }

    private class ViewHolder {
        TextView textViewTenCmt, textViewCmt, textViewDanhGiaCmt;
        ImageView hinhAnh;
    }
}
