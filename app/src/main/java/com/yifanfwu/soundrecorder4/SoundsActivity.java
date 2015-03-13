package com.yifanfwu.soundrecorder4;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SoundsActivity extends Activity {

    private File root;
    public MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);
        Intent intent = getIntent();

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Tapedeck");

        if (root.list().length == 0) {
            Toast.makeText(this,R.string.no_tapes,Toast.LENGTH_SHORT).show();
        }

        File[] fileList = root.listFiles();
        final String[] stringList = new String[fileList.length];

        for (int i = 0; i < fileList.length; i++) {
            stringList[i] = fileList[i].getName().toString();
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, stringList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Tapedeck" + File.separator + stringList[position]);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),R.string.no_play,Toast.LENGTH_SHORT).show();
                }
                mediaPlayer.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sounds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
