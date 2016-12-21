package com.cocomap.coco.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocomap.coco.R;
import com.cocomap.coco.activity.TopicDetailActivity;
import com.cocomap.coco.pojo.PostModel;
import com.cocomap.coco.pojo.PostRetrieveResponse;
import com.cocomap.coco.pojo.TopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emrah on 21/12/2016.
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {

    Context context;
    private List<PostRetrieveResponse> list;

    public List<PostRetrieveResponse> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<PostRetrieveResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public SearchRecyclerAdapter(Context context) {

        this.context = context;
    }

    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.ViewHolder holder, final int position) {
        holder.text.setText(getList().get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicDetailActivity.class);
                intent.putExtra("focused_topic_id", getList().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
