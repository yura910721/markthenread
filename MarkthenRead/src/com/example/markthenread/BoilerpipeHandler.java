package com.example.markthenread;

import java.net.URL;

import org.xml.sax.InputSource;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import android.os.AsyncTask;

final class MyResult {
    private final String first;
    private final String second;

    public MyResult(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
}

public class BoilerpipeHandler extends AsyncTask<String,MyResult, MyResult>{

	@Override
	protected MyResult doInBackground(String...urls) {
		// TODO Auto-generated method stub
		String title;
		String text;
		try {
       		
          	// get page title 
          		
           		final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(urls[0]));
          	    final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
        	    title = doc.getTitle();
    	    
          	   URL url = new URL(urls[0]);
          		InputSource is = new InputSource();
          		is.setEncoding("UTF-8");
          		is.setByteStream(url.openStream());

          		text = ArticleExtractor.INSTANCE.getText(is);
          		
			              		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		return new MyResult(title, text);
	}
	

}
