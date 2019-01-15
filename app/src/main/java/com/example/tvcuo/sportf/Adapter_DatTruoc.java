package com.example.tvcuo.sportf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class Adapter_DatTruoc extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DonDatTruoc> datTruocList;

    public Adapter_DatTruoc(Context context, int layout, List<DonDatTruoc> datTruocList) {
        this.context = context;
        this.layout = layout;
        this.datTruocList = datTruocList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
