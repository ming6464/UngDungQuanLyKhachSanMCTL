package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemServiceSpinnerAdapter extends BaseAdapter {
    private List<Services> list;
    private NumberFormat format;
    private Services obj;

    public ItemServiceSpinnerAdapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }
    public void setDate(List<Services> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        private TextView tv_name,tv_price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_spinner,parent,false);
            viewHolder.tv_name = convertView.findViewById(R.id.itemServiceSpinner_tv_name);
            viewHolder.tv_price = convertView.findViewById(R.id.itemServiceSpinner_tv_price);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        obj = list.get(position);
        viewHolder.tv_name.setText(obj.getName());
        viewHolder.tv_price.setText("(" +format.format(obj.getPrice()) + "K)");
        return convertView;
    }
}
