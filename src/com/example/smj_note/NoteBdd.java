package com.example.smj_note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class NoteBdd {

	//La table note
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";

	private static final String KEY_ID_NOTE = "idNote";

	private static final String TAG = "NotesBdd";
	private DatabaseHelper maBase;
	private SQLiteDatabase bdd;

	//Création de la base de données
	private static final String DATABASE_CREATE =
			"create table notes (_id integer primary key autoincrement, "
					+ "title text not null, body text not null);";



	private static final String DATABASE_NAME = "data";
	private static final String TABLE_NOTE = "notes";
	private static final int DATABASE_VERSION = 10;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
	}

	//Constructeur avec un contexte
	public NoteBdd(Context ctx) {
		this.mCtx = ctx;
	}

	//Ouverture de la base en écriture
	public NoteBdd open() throws SQLException {
		maBase = new DatabaseHelper(mCtx);
		bdd = maBase.getWritableDatabase();
		return this;
	}

	//Fermeture de la base
	public void close() {
		maBase.close();
	}

	//------------------------------CREATE-------------------------------------

	//Création d'un ContentValues (fonctionne comme une HashMap)
	//On lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
	//On insère l'objet dans la base via le ContentValues
	public long createNote(String title, String body) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_BODY, body);
		return bdd.insert(TABLE_NOTE, null, values);
	}

	//------------------------------DELETE-------------------------------------
	
	//Suppression d'une note grace à son id
	public boolean deleteNote(long rowId) {

		return bdd.delete(TABLE_NOTE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	//------------------------------SELECT-------------------------------------
	
	//Un curseur qui retourne toutes les notes
	public Cursor fetchAllNotes() {

		return bdd.query(TABLE_NOTE, new String[] { KEY_ROWID, KEY_TITLE,
				KEY_BODY }, null, null, null, null, null);
	}

	//Retourne un curseur qui se positionne grace à l'id en argument
	public Cursor fetchNote(long rowId) throws SQLException {

		Cursor mCursor =

				bdd.query(true, TABLE_NOTE, new String[] { KEY_ROWID,
						KEY_TITLE, KEY_BODY}, KEY_ROWID + "=" + rowId, null, null, null, null,
						null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	//------------------------------UPDATE-------------------------------------

	////Création d'un ContentValues (fonctionne comme une HashMap)
	//On lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
	//On modifie un objet grace à l'id dans la base via le ContentValues
	public boolean updateNote(long rowId, String title, String body) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_BODY, body);
		return bdd.update(TABLE_NOTE, values, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
