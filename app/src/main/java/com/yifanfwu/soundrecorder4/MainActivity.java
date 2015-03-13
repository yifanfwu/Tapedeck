package com.yifanfwu.soundrecorder4;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

    private MediaRecorder mAudioRecorder;
    String outputFile = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Tapedeck";
        File folder = new File(directoryPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {

            String soundName = "sound";

            @Override
            public void onClick(View v) {

                if (button.getText().toString().equals(getResources().getString(R.string.start_record))) {
                    button.setText(getResources().getString(R.string.stop_record), TextView.BufferType.NORMAL);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                    soundName = (DateFormat.format("dd_MM_yyyy_hh_mm_ss", new java.util.Date()).toString());


                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tapedeck/"
                            + soundName + ".3gp";

                    mAudioRecorder = new MediaRecorder();
                    mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mAudioRecorder.setOutputFile(outputFile);

                    try {
                        mAudioRecorder.prepare();
                        mAudioRecorder.start();

                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    button.setText(getResources().getString(R.string.start_record), TextView.BufferType.NORMAL);

                    chronometer.stop();
                    chronometer.setBase(SystemClock.elapsedRealtime());

                    mAudioRecorder.stop();
                    mAudioRecorder.reset();

                    Toast.makeText(getApplicationContext(), "Recording: " + soundName
                            + " saved", Toast.LENGTH_SHORT).show();

                }
            }
        });

        final Button mySounds = (Button) findViewById(R.id.button2);
        mySounds.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SoundsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
