package com.bermet.dictionary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BookmarkAdapter extends BaseAdapter {
    private ListItemListener listener;
    private ListItemListener listenerBtnDelete;
    Context mContext;
   ArrayList<String> mSource;


    public BookmarkAdapter(Context context,String[]source){
        this.mSource = new ArrayList<>(Arrays.asList(source));
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return mSource.size();
    }

    @Override
    public Object getItem(int i) {

        return mSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.bookmark_layout_item,viewGroup,false);
            viewHolder.textView=view.findViewById(R.id.tvWord);
            viewHolder.btnDelete=view.findViewById(R.id.btnDelete);

            view.setTag(viewHolder);



        } else {
           viewHolder=(ViewHolder) view.getTag();


        }
        viewHolder.textView.setText(mSource.get(i));
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(listener != null)
                listener.onItemClick(i);

            }});
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenerBtnDelete != null)
                    listenerBtnDelete.onItemClick(i);
            }


        });
        return view;
    }
    public void removeItem(int position){
        mSource.remove(position);

    }
    class ViewHolder{
        TextView textView;
        ImageView btnDelete;


    }
    public void setOnItemClick(ListItemListener listItemListener){
        this.listener=listItemListener;




    }


    public void setOnItemDeleteClick(ListItemListener listItemListener){
        this.listenerBtnDelete=listItemListener;


    }}