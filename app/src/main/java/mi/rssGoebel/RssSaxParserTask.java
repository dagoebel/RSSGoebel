package mi.rssGoebel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class RssSaxParserTask extends AsyncTask <String, Integer, List<Article>> {

	//ArticlesListAdapter articleListAdapter;
	DBoperacje baza;
	List<Article> articles;
	RssSaxHandler rssSaxHandler;
	MyDialogFragment dialogPobierz;
	FragmentActivity activity; //context
	
	//Konstruktor
	public RssSaxParserTask( 
			//ArticlesListAdapter articleListAdapter, 
			DBoperacje baza,
			MyDialogFragment dialogPobierz,
			FragmentActivity activity) {
	
		//this.articleListAdapter = articleListAdapter;
		this.baza = baza;
		this.dialogPobierz = dialogPobierz;
		this.activity = activity;
	}
	
	
	//
	@Override
	protected void onPreExecute() {
		dialogPobierz.show(activity.getSupportFragmentManager(), "missiles");
	}

	@Override
	protected List<Article> doInBackground(String... urls) {
  
		articles = new ArrayList<Article>(); 

 
		for (int i = 0; i < urls.length; i++) {
			try {
				
				articles = parseXml(urls[0]); 
				
				publishProgress((int) (((i+1) / (float) urls.length) * 100));
      
	  
			} catch (ParserConfigurationException pce){
				Log.e("SAX XML", "sax parse error", pce);
				
			} catch (SAXException se){
				Log.e("SAX XML", "sax error", se);
			
			} catch (IOException e) {
				Log.e("SAX XML", "Exception", e);
			}
		}
  
		return articles;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {          
		dialogPobierz.setPostep(values[0]);
	}


	@Override
	protected void onPostExecute(List<Article> articles) {

		dialogPobierz.dismiss();
		
		if (articles.isEmpty()) {
			 Log.d("SAX XML ARTICLES", "ARTICLES ARE EMPTY !!!");
	       
	         //Toast.makeText(getApplicationContext(),
			 Toast.makeText(activity,
	         	 "???", Toast.LENGTH_SHORT).show();
	         return;
	    }
		
		aktualizacjaBazy(articles);	
		// aktualizacjaAdaptera();

		
	}




private List<Article> parseXml(String strUrl) throws ParserConfigurationException,SAXException,IOException {
//private List<Article> parseXml(String strUrl) throws Exception {
	  
        URL xmlUrl = new URL(strUrl);
	        
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
	        
        rssSaxHandler = new RssSaxHandler();
        reader.setContentHandler(rssSaxHandler);
	       
        InputSource inputSource = new InputSource(xmlUrl.openStream());
        reader.parse(inputSource);
	        
        return rssSaxHandler.getArticles(); 
}


private void aktualizacjaBazy(List<Article> articles) {
	 
	baza.deleteAll();
	
	for (Article article : articles)
		baza.addToDatabase(new Article(
				article.getTitle(),
				article.getDescription(), 
				article.getUrl(), 
				article.getPubDate(), 
				article.getCategory()));
	    
}

/*
private void aktualizacjaAdaptera() {

	articleListAdapter.clear();
	articleListAdapter.addAll(baza.getAllData());
	articleListAdapter.notifyDataSetChanged();
}
*/

}

