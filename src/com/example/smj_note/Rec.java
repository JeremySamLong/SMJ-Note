package com.example.smj_note;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Rec extends Activity
{
	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rec_accueil);

		
		//File rep = new File("/SMJ-Note");
		//rep.mkdirs();
		
		
		OUTPUT_FILE=Environment.getExternalStorageDirectory().getAbsolutePath();
		OUTPUT_FILE += "/audiorecorder.3gp";
	}

	public void onClickRec (View v){
		switch (v.getId()) {
		case R.id.buttonRec:
			try{
				beginRecording();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.buttonStopRec:
			try{
				stopRecording();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.buttonPlayRec:
			try{
				playRecording();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.buttonStopPlay:
			try{
				stopPlayback();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	private void stopPlayback() {
		Toast.makeText(getApplicationContext(), "Stop play", Toast.LENGTH_SHORT).show();
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	private void playRecording() throws Exception{
		Toast.makeText(getApplicationContext(), "Bouton play", Toast.LENGTH_SHORT).show();
		ditchMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
	
	private void ditchMediaPlayer() {
		if(mediaPlayer != null)
		{
			try{
				mediaPlayer.release();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void stopRecording() {
		Toast.makeText(getApplicationContext(), "Bouton stop rec", Toast.LENGTH_SHORT).show();
		if(recorder != null)
			recorder.stop();
	}

	private void beginRecording() throws Exception {
		Toast.makeText(getApplicationContext(), "Bouton rec", Toast.LENGTH_SHORT).show();

		ditchMediaRecorder();
		File outFile = new File(OUTPUT_FILE);
		
		if(outFile.exists())
			outFile.delete();
		
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(OUTPUT_FILE);
		recorder.prepare();
		recorder.start();
		
	}

	private void ditchMediaRecorder() {
		if(recorder != null)
			recorder.release();
	}

}
	/*private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {

    	mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    public Rec() {
    	int indiceNote = 0 ;

    	Random rand = new Random();
    	indiceNote = rand.nextInt(1000 - 0 + 1) + 0;

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest"+indiceNote+".3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }*/