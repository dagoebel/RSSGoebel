package mi.rssGoebel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBOperation {
	
	private SQLiteDatabase db; 	
	private DBopenHelper dbOpenHelper;
	
	
	public DBOperation(Context context) {
		  dbOpenHelper = new DBopenHelper(context);
	  }
	
	//Open DataBase
	public void open() throws SQLException {
	    db = dbOpenHelper.getWritableDatabase();
	}

	//Close DataBase
	public void close() {
	    dbOpenHelper.close();
	}
	
	
	 /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
	
    //orginal

    public void addToDatabase(Article article) {
    	
        ContentValues values = new ContentValues();
        values.put(DBopenHelper.NAZWA_KOLUMNY_TITLE, article.getTitle());
        values.put(DBopenHelper.NAZWA_KOLUMNY_DESCRIPTION, article.getDescription()); 
        values.put(DBopenHelper.NAZWA_KOLUMNY_URL, article.getUrl()); 
        values.put(DBopenHelper.NAZWA_KOLUMNY_PUBDATE, article.getPubDate()); 
        values.put(DBopenHelper.NAZWA_KOLUMNY_CATEGORY, article.getCategory()); 
        
        // Inserting Row
        db.insert(DBopenHelper.NAZWA_TABELI, null, values);
        //bazaDanych.close(); // Closing database connection
    }

    public List<Article> getAllData() {
       
    	List<Article> lista = new ArrayList<Article>();
             
        // Select All Query      
        String selectQuery = "SELECT  * FROM " + DBopenHelper.NAZWA_TABELI;
        Cursor kursor = db.rawQuery(selectQuery, null);

        
        //int licznik = 1;
        		
        // looping through all rows and adding to list
        if (kursor.moveToFirst()) {
            do {
                Article wynik = new Article();

                wynik.setId(Integer.parseInt(kursor.getString(0)));
                wynik.setTitle(kursor.getString(1));
                wynik.setDescription(kursor.getString(2));
                wynik.setUrl(kursor.getString(3));
                wynik.setPubDate(kursor.getString(4));
                wynik.setCategory(kursor.getString(5));
                

                //wynik.setNr(licznik++);
                
                // Adding contact to list
                lista.add(wynik);
                
            } while (kursor.moveToNext());
        }
 
        kursor.close();
        // return contact list
        return lista;
    }
    
    // Getting article where category
    public List<Article> getCategory(String category) {
       
    	List<Article> lista = new ArrayList<Article>();
             
        // Select All Query      
        String selectQuery = "SELECT  * FROM " + DBopenHelper.NAZWA_TABELI +" WHERE category = '"+category+"'";
        Cursor kursor = db.rawQuery(selectQuery, null);
        		
        // looping through all rows and adding to list
        if (kursor.moveToFirst()) {
            do {
                Article wynik = new Article();

                wynik.setId(Integer.parseInt(kursor.getString(0)));
                wynik.setTitle(kursor.getString(1));
                wynik.setDescription(kursor.getString(2));
                wynik.setUrl(kursor.getString(3));
                wynik.setPubDate(kursor.getString(4));
                wynik.setCategory(kursor.getString(5));
                

                //wynik.setNr(licznik++);
                
                // Adding contact to list
                lista.add(wynik);
                
            } while (kursor.moveToNext());
        }
 
        kursor.close();
        // return contact list
        return lista;
    }
    
    // Getting Categories by Articles
    public Set<String> getCategoryColumn() {
       
    	Set<String> lista = new HashSet<String>();
             
        // Select All Query      
        String selectQuery = "SELECT category FROM " + DBopenHelper.NAZWA_TABELI;
        Cursor kursor = db.rawQuery(selectQuery, null);
        		
        // looping through all rows and adding to list
        if (kursor.moveToFirst()) {
            do {
                //Article wynik = new Article();
            	String category;
                

                category = kursor.getString(0);
                
                // Adding contact to list
                lista.add(category);
                
            } while (kursor.moveToNext());
        }
 
        kursor.close();
        // return contact list
        return lista;
    }
    

    public void deleteAll() {
        db.delete(DBopenHelper.NAZWA_TABELI, null, null);
    }
    
    
}
