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

        String content = list.get(position).getContent();
        for(int i = 0; i < list.get(position).getTags().size(); i++){
            content +=" #"+ list.get(position).getTags().get(i).getName();
        }
        holder.text.setText(content);
        holder.usernameTextView.setText("@" + list.get(position).getUser().getUsername());

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
        TextView usernameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        }
    }
}
