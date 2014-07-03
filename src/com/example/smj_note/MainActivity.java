package com.example.smj_note;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);
	}

	public void onClickNote (View v)
	  {
		  Intent intent = new Intent(this, NoteSQLite.class);
		  this.startActivity(intent);
	  }
	
	public void onClickRec (View v)
	  {
		  Intent intent = new Intent(this, Rec.class);
		  this.startActivity(intent);
	  }
}