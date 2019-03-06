package com.abel.miequipo.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abel.miequipo.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListView extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> dummyList;

    public CustomListView(Context context, int resource, List<String> dummyList) {
        super(context, resource, dummyList);
        this.mContext = context;
        this.dummyList = dummyList;
    }

    public CustomListView(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = dummyList.get(position);
        String title = item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        CircleImageView ivIcon;


        convertView = inflater.inflate(R.layout.item_busqueda_jugador, parent, false);
        ivIcon = convertView.findViewById(R.id.imageView);
        TextView tvTitleItem = convertView.findViewById(R.id.text1);
        tvTitleItem.setText(title);
        return convertView;
    }
}