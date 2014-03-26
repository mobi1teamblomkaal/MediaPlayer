package com.example.app;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;


public class FileBrowserActivity extends ListActivity {
    private File[] data;
    private File root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new File("/");
        data = root.listFiles();

        setListAdapter(new FileAdapter(getBaseContext(), data));
    }
}
