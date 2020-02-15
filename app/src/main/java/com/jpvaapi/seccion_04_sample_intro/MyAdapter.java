package com.jpvaapi.seccion_04_sample_intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juan P on 8/02/20
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<Car> list;
    private int layuot;

    public MyAdapter(Context context, List<Car> list, int layout) {
        this.context = context;
        this.list = list;
        this.layuot = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Car getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layuot, null);
            vh = new ViewHolder();
            vh.vin = convertView.findViewById(R.id.textViewVIN);
            vh.name = convertView.findViewById(R.id.textViewName);
            vh.color = convertView.findViewById(R.id.textViewColor);
            convertView.setTag(vh);
        } else
            vh = (ViewHolder) convertView.getTag();

        Car currentCar = list.get(position);

        vh.vin.setText(currentCar.getVIN() + "");
        vh.name.setText(currentCar.getName());
        vh.color.setText(currentCar.getColor());

        return convertView;
    }

    public class ViewHolder {
        TextView vin;
        TextView name;
        TextView color;
    }
}
