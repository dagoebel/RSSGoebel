

package mi.rssGoebel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// SQLiteOpenHelper
public class DBopenHelper extends SQLiteOpenHelper{


	  private static final int WERSJA_BAZY = 2;


	  private static final String NAZWA_BAZY = "micromate_rss.db";

	  public static final String NAZWA_TABELI = "articles";

	  public static final String NAZWA_KOLUMNY_ID = "id";
	  public static final String NAZWA_KOLUMNY_TITLE = "title";   
	  public static final String NAZWA_KOLUMNY_DESCRIPTION = "description";   		 
	  public static final String NAZWA_KOLUMNY_URL = "url";  
	  public static final String NAZWA_KOLUMNY_PUBDATE = "pubDate";  
	  public static final String NAZWA_KOLUMNY_CATEGORY = "category";  
	  

	  // Database creation sql statement
	  private static final String UTWORZ_TABLICE = 
		  "create table " + NAZWA_TABELI + "(" + 
	      NAZWA_KOLUMNY_ID + " integer primary key autoincrement, " + 
	      NAZWA_KOLUMNY_TITLE + " text not null," +
	      NAZWA_KOLUMNY_DESCRIPTION + " text not null," +
	      NAZWA_KOLUMNY_URL + " text not null," + 
	      NAZWA_KOLUMNY_PUBDATE + " text not null," +
	      NAZWA_KOLUMNY_CATEGORY + " text not null);";


	  public DBopenHelper(Context context) {
	    super(context, NAZWA_BAZY, null, WERSJA_BAZY);
	  }


	  @Override
	  public void onCreate(SQLiteDatabase bazaDanych) {
		  
	    bazaDanych.execSQL(UTWORZ_TABLICE);
	  
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
		Log.w(DBopenHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
	   
	    db.execSQL("DROP TABLE IF EXISTS " + NAZWA_TABELI);
	    
	    onCreate(db);
	  }

	} 