/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.smj_note;

import android.R.bool;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEdit extends Activity {

	private EditText mTitleText;
	//private EditText mBodyText;
	private Long mRowId;
	private NotesDbAdapter mDbHelper;
	private Bundle extras;
	// voir si le boutton confirm est cliqué
	private boolean ok=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.note_edit);
		setTitle("Edit note");
		mTitleText = (EditText) findViewById(R.id.title);
		//mBodyText = (EditText) findViewById(R.id.body);
		
			Button confirmButton = (Button) findViewById(R.id.confirm);
	
			mRowId = (savedInstanceState == null) ? null
					: (Long) savedInstanceState
							.getSerializable(NotesDbAdapter.KEY_ROWID);
			if (mRowId == null) {
				extras = getIntent().getExtras();
				
				
				if(extras != null)
				{
					mRowId= extras.getLong(NotesDbAdapter.KEY_ROWID);
				}
				else
				{
					mRowId=null;
				}
					
			}
	
			populateFields();
			
				
			confirmButton.setOnClickListener(new View.OnClickListener() {
	
				public void onClick(View view) {
						ok=true;
						setResult(RESULT_OK);
						finish();
				}
	
			});
			
		}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			mTitleText.setText(note.getString(note
					.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
			/*mBodyText.setText(note.getString(note
					.getColumnIndexOrThrow(FichesDbAdapter.KEY_BODY)));*/
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		long id=0;
		String title = mTitleText.getText().toString();
		//String body = mBodyText.getText().toString();
		if(mTitleText.getText().toString().isEmpty() && ok==false)
			Toast.makeText(getApplicationContext(), "Aucune note enregistrée", Toast.LENGTH_SHORT).show();
		else if(!mTitleText.getText().toString().isEmpty() && ok==true)
		{
			
			if (mRowId == null) //ajout
			{
				
				//id = mDbHelper.createFiche(title, body);
				id = mDbHelper.createNote(title);
				Toast.makeText(getApplicationContext(), "La note "+ mTitleText.getText().toString()+ " a été enregistrée", Toast.LENGTH_SHORT).show();
				mRowId =id;

				if (id > 0) {
					mRowId = id;
				}
				
			}
			 else  //edition
			 {
				
				String ancienTexte;
				Cursor note = mDbHelper.fetchNote(mRowId);
				ancienTexte=(note.getString(note
						.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)).toString());
				if(!title.contentEquals(ancienTexte))
				{
					//mDbHelper.updateNote(mRowId, title, body);
					mDbHelper.updateNote(mRowId, title);
					Toast.makeText(getApplicationContext(), "La note "+ ancienTexte+ " a été modifiée en "+ mTitleText.getText().toString(), Toast.LENGTH_SHORT).show();
				}	
					
			}
		}		
	}

}
