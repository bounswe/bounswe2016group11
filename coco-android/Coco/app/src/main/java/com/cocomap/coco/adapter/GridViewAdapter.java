package com.cocomap.coco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cocomap.coco.R;


/**
 * Created by Emrah on 07/11/2016.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private final String[] thumbnailUrls;

    public GridViewAdapter(Context context, String[] thumbnailUrls) {
        this.context = context;
        this.thumbnailUrls = thumbnailUrls;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.gridview_item_layout, null);


        } else {
            gridView = convertView;
        }

        // set image based on selected text
        TextView textView = (TextView) gridView
                .findViewById(R.id.gridItemTextView);

        textView.setText(thumbnailUrls[position]);


        return gridView;
    }

    @Override
    public int getCount() {
        return thumbnailUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return thumbnailUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
