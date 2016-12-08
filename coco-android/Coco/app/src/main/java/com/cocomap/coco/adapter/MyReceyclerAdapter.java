package com.cocomap.coco.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocomap.coco.R;
import com.cocomap.coco.pojo.PostModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinan on 06.12.2016.
 */
public class MyReceyclerAdapter extends RecyclerView.Adapter<MyReceyclerAdapter.ViewHolder>{

    Context context;
    private List<PostModel> list;

    public List<PostModel> getList() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public MyReceyclerAdapter(Context context) {

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText(list.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public void setList(List<PostModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
