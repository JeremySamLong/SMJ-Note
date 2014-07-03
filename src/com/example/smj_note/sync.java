package com.example.smj_note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sync extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msgsync);

	}
	
	public void onClickConxSyn (View v)
	  {
		setContentView(R.layout.conxsyn);
	  }
	

}
