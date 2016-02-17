package com.example.markthenread;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Viewer extends Activity {

	String value="";
	private TextView wb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			  value = extras.getString("Data to pass");
			}
	    
	setContentView(R.layout.activity_viewer);
	
	    wb = (TextView) findViewById(R.id.textView1);
		
	DatabaseHandler db = new DatabaseHandler(this);
		
	    String content = db.getContent(value);
		wb.setMovementMethod(new ScrollingMovementMethod());
		wb.setText(content);
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewer, menu);
		return true;
	}
	
}
