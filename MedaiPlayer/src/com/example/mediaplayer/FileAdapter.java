package com.example.mediaplayer;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ArrayAdapter;

public class FileAdapter extends ArrayAdapter<File> {

	public FileAdapter(Context context, int res, File[] objects) {
		super(context, res, objects);
		// TODO Auto-generated constructor stub
	}
}
