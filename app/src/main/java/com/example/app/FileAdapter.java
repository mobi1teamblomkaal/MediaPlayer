package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by sandbeck on 3/26/14.
 */
public class FileAdapter extends ArrayAdapter<File> {
    private File[] data;
    private Context context;

    public FileAdapter(Context context, File[] objects) {
        super(context, R.layout.file_list_item, objects);
        this.data = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.file_list_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.iconImageView);
        TextView name = (TextView) view.findViewById(R.id.nameTextView);
        TextView description = (TextView) view.findViewById(R.id.descriptionTextView);

        /*
         * image resources
         * folder: ic_menu_archive
         * media file: ic_menu_play_clip
         * other:
         */
        image.setImageDrawable(view.getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        name.setText(data[position].getName());

        return view;
    }
}
