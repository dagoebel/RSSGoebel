

package mi.rssGoebel;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import mi.rssGoebel.R;

public class MainActivity extends ActionBarActivity {
	
	private ListView listView;
	private ArrayAdapter<String> adapter_listy;
	private DBoperacje baza;
	private List<String> categories;
	private Button refreshButton;
	private RssSaxParserTask rssSaxParserTask;
	private MyDialogFragment dialogPobierz;

    ActionBar ab;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        ab = getSupportActionBar();
        ab.setTitle("RSSGoebel");
		
		listView = (ListView)findViewById(R.id.categoryList);
        refreshButton = (Button)findViewById(R.id.refreshButton);
		
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


        refreshButton.setOnClickListener(new View.OnClickListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_settings);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return super.onCreateOptionsMenu(menu);
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
