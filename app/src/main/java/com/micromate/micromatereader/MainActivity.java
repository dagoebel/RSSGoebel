

package com.micromate.micromatereader;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	
	private ListView listView;
	private ArrayAdapter<String> adapter_listy;
	private DBoperacje baza;
	private List<String> categories;
	private Button uaktualnijBazeButton;
	private RssSaxParserTask rssSaxParserTask;
	private MyDialogFragment dialogPobierz;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView)findViewById(R.id.categoryList);
		uaktualnijBazeButton = (Button)findViewById(R.id.button1);
		
		dialogPobierz = new MyDialogFragment();
		
		baza = new DBoperacje(this);
		baza.open();
		
		//Convert HasSet to ArrayList
		categories = new ArrayList<String>(baza.getCategoryColumn()); 
		categories.add("Alle"); //adding all categories to the list
		
		adapter_listy = new ArrayAdapter<String>(this, R.layout.category_list_item, categories);
		listView.setAdapter(adapter_listy);		
		
		// listening to single list item on click
        listView.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
               
              // selected item 
              String category = ((TextView) view).getText().toString();
               
              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(), ArticlesListActivity.class);
             
              // sending data to new activity
              i.putExtra("category", category);
              startActivity(i);
             
          }
        });
        
    	//AKTUALIZACJA BAZY 	
		uaktualnijBazeButton.setOnClickListener(new View.OnClickListener() {   
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
							
				String url = "http://www.th-nuernberg.de/institutionen/fakultaeten/informatik/rss2.xml";


				
				rssSaxParserTask = new RssSaxParserTask(baza, dialogPobierz, MainActivity.this);
				rssSaxParserTask.execute(url,url,url,url,url,url,url,url,url,url); //jest 10, wiec po 10%	
			}
		});
		
	}
	
	 @Override
	  protected void onResume() {
	    baza.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    baza.close();
	    super.onPause();
	  }

	
}
