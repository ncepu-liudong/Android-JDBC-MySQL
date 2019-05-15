package com.example.graduationproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graduationproject.R;

public class MyItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private final String []text = {"我的账号","退出登录"};
    private final int []img_resource={R.drawable.wodezhanghao, R.drawable.tuichudenglu};

    public  MyItemAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        MyViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.my_list_item,null);
            holder = new MyViewHolder();
            holder.imageView = convertView.findViewById(R.id.item_logo);
            holder.textView = convertView.findViewById(R.id.item_text);
            holder.ImageView = convertView.findViewById(R.id.item_in);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder)convertView.getTag();
        }
        holder.imageView.setImageResource(img_resource[position]);
        holder.textView.setText(text[position]);
        return convertView;
    }
    class MyViewHolder{
        android.widget.ImageView imageView;
        ImageView ImageView;
        TextView textView;
    }
}
