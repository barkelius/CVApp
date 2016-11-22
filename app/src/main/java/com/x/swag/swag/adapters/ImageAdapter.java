package com.x.swag.swag.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.x.swag.swag.R;

/**
 * Created by barke on 2016-03-28.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(135, 135));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Integer piece = mThumbIds[position];
        if(piece != null)
            imageView.setImageResource(piece);
        imageView.setBackgroundColor((position % 2 == ((position/8)%2) ? Color.BLACK : Color.WHITE));
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.rlt60, R.drawable.nlt60, R.drawable.chess_blt60, R.drawable.chess_klt60,
            R.drawable.qlt60, R.drawable.chess_blt60, R.drawable.nlt60, R.drawable.rlt60,
            R.drawable.plt60, R.drawable.plt60, R.drawable.plt60, R.drawable.plt60,
            R.drawable.plt60, R.drawable.plt60, R.drawable.plt60, R.drawable.plt60,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            R.drawable.pdt60, R.drawable.pdt60, R.drawable.pdt60, R.drawable.pdt60,
            R.drawable.pdt60, R.drawable.pdt60, R.drawable.pdt60, R.drawable.pdt60,
            R.drawable.rdt60, R.drawable.ndt60, R.drawable.chess_bdt60, R.drawable.chess_kdt60,
            R.drawable.qdt60, R.drawable.chess_bdt60, R.drawable.ndt60, R.drawable.rdt60
    };
}
