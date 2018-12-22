package com.example.kisha.androidphotos79;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kisha.androidphotos79.model.Photo;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Photo> photos;
    LayoutInflater inflater = null;

    public CustomAdapter(Activity context, ArrayList<Photo> photos){
        this.context = context;
        this.photos = photos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder vh;
        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.activity_image_list_item, null);
            vh = new ViewHolder(v);
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
        String strUri = photos.get(position).getUri();
        Uri uri = Uri.parse(strUri);
        vh.imageView.setImageURI(uri);
        return v;
    }

    class ViewHolder {
        ImageView imageView;
        public ViewHolder(View base){
            imageView = (ImageView) base.findViewById(R.id.ivImageList);
        }
    }

}
