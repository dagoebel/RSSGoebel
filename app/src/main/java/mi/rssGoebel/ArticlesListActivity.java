package mi.rssGoebel;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import mi.rssGoebel.R;

public class ArticlesListActivity extends FragmentActivity {

	private	ListView listView;	
	private List<Article> articles; 
	private ArticlesListAdapter articleListAdapter;   
	private DBoperacje baza;
	private Intent intent;
	private String category;
	private TextView categoryTextView;

    private String feedUrl;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_articles_list);
		
		listView = (ListView)findViewById(R.id.listView1);
		categoryTextView = (TextView)findViewById(R.id.textView1); 
		
		articles = new ArrayList<Article>();
		baza = new DBoperacje(this);

		intent = getIntent();
	    category = intent.getStringExtra("category");

	    categoryTextView.setText(category);
	    
		baza.open(); 	
		
		if (category.equals("Alle"))
			getAllData();
		else 
			getFeedData();
			

		listView.setOnItemClickListener(new OnItemClickListener() {   
 
		        @Override
		        public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
		        	
		            Intent myIntent = new Intent(getApplicationContext(), ArticleActivity.class);

		            myIntent.putExtra("articleTitle",articles.get(pos).getTitle());
		            myIntent.putExtra("articleCategory",articles.get(pos).getCategory());
		            myIntent.putExtra("articleUrl", articles.get(pos).getUrl());
		            
		            startActivity(myIntent);
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
	  
	  
	  private void getAllData() {
		     
			articles = baza.getAllData();

		    articleListAdapter = new ArticlesListAdapter(this, R.layout.activity_articles_list, articles);
			listView.setAdapter(articleListAdapter);
		
		}
	  
	  private void getFeedData() {
		     
			articles = baza.getCategory(category);
				    
		    articleListAdapter = new ArticlesListAdapter(this, R.layout.activity_articles_list, articles);
			listView.setAdapter(articleListAdapter);
		
		}

	
}
