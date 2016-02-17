package com.example.markthenread;

import java.io.IOException;


import android.os.Bundle;
//import android.renderscript.Element;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;


public class ManAddActivity extends Activity {

	EditText URLField;
	String title;
	String content;
	Document doc;
	String URL;
	Elements testString;
	String[] selectors = {"div[class=copy post-body]+p","div[class=row post-content]","div[class=body-copy]","div[class=article_body font1 size4]"}; //Engadget,Lifehacker,Techcrunch,Naver News 
	//TextView outputTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_man_add);
		final DatabaseHandler db = new DatabaseHandler(this);
		//outputTextView = (TextView)findViewById(R.id.textView1);
		final Button GoButton = (Button)findViewById(R.id.button1);
	    URLField  = (EditText)findViewById(R.id.editText1);
	    
	      
	    GoButton.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("ShowToast")
			public void onClick(View v) {
                // Perform action on click
            	
            	
            	try {
             
            		// need http protocol
            		URL = URLField.getText().toString();
            		doc = Jsoup.connect("http://"+URLField.getText().toString()+"").get();
             
            		
            		// get page title
            		title = doc.title();
                	
            	} catch (IOException e) {
            		e.printStackTrace();
            	}


            	if(URL.contains("engadget")){
            		Toast.makeText(getApplicationContext(), "Engadget", Toast.LENGTH_LONG).show();
            		testString = doc.select("div[itemprop=text]");
            	}
            	else if(URL.contains("techcrunch")){
            		Toast.makeText(getApplicationContext(), "Techcrunch", Toast.LENGTH_LONG).show();
            		testString = doc.select(selectors[2]);
            	}
            	else if(URL.contains("lifehacker")){
            		Toast.makeText(getApplicationContext(), "Lifehacker", Toast.LENGTH_LONG).show();
            		testString = doc.select(selectors[1]);
            	}
            	else if(URL.contains("news.naver")){
            		Toast.makeText(getApplicationContext(), "Naver News", Toast.LENGTH_LONG).show();
            		testString = doc.select(selectors[3]);
            	}
            	else {
            		Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_LONG).show();
            		testString = doc.select("p");
            	}
            	
            	
            	
            	String test = testString.text();
            	db.addContact(new Contact(title,test));

        		Intent i = new Intent(ManAddActivity.this, LaunchActivity.class);
    			startActivity(i);
        }
			
            });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.man_add, menu);
		return true;
	}

}
