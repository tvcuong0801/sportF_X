package com.example.tvcuo.sportf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter_HinhAnh extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HinhAnh> hinhAnhList;

    Adapter_HinhAnh(Context context, int layout, List<HinhAnh> hinhAnhList) {
        this.context = context;
        this.layout = layout;
        this.hinhAnhList = hinhAnhList;
    }

    @Override
    public int getCount() {
        return hinhAnhList.size();
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
        ImageView hinhAnh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Adapter_HinhAnh.ViewHolder holder;
        if(convertView==null){
            holder= new Adapter_HinhAnh.ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.hinhAnh=convertView.findViewById(R.id.imageView_item_hinhanh);
            convertView.setTag(holder);
        }else {
            holder= (Adapter_HinhAnh.ViewHolder) convertView.getTag();
        }
        HinhAnh hinhAnh= hinhAnhList.get(position);


        String url= hinhAnh.getUrl();
        Picasso.get()
                .load(url)
                .resize(150, 150)
                .centerCrop()
                .into(holder.hinhAnh);

        return convertView;
    }
}
