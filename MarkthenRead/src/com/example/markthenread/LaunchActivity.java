package com.example.markthenread;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.xml.sax.InputSource;


import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class LaunchActivity extends ListActivity {

	private ListView lv1;
	private ArrayList<String> arraylist = new ArrayList<String>();
	private String[] array = new String[10];
	String value = "";
	final Context context = this;
	
	String title;
	int state=0;
	String text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final DatabaseHandler db = new DatabaseHandler(this);
		arraylist = db.getAllContacts();
		array = db.populatearray();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_launch,arraylist));
		lv1 = getListView();
		
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i1 = new Intent(LaunchActivity.this, Viewer.class);
    			i1.putExtra("Data to pass",array[position]);
    			startActivity(i1);
			}
			
			
		});
		lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

		      public boolean onItemLongClick (AdapterView<?> parent, View view, final int position, long id){
					// custom dialog
		    	  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		  				context);
		   
		  			// set title
		  			alertDialogBuilder.setTitle("Delete");
		   
		  			// set dialog message
		  			alertDialogBuilder
		  				.setMessage("Do you want to delete this item?")
		  				.setCancelable(false)
		  				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
		  					public void onClick(DialogInterface dialog,int id) {
		  						// if this button is clicked, close
		  						// current activity
		  						db.deleteContact(array[position]);
		  						finish();
            		            startActivity(getIntent());
		  					}
		  				  })
		  				.setNegativeButton("No",new DialogInterface.OnClickListener() {
		  					public void onClick(DialogInterface dialog,int id) {
		  						// if this button is clicked, just close
		  						// the dialog box and do nothing
		  						dialog.cancel();
		  					}
		  				});
		   
		  				// create alert dialog
		  				AlertDialog alertDialog = alertDialogBuilder.create();
		   
		  				// show it
		  				alertDialog.show();
						return true;
				  }
		    });

		final Intent intent = getIntent();
		if (savedInstanceState == null && intent != null) {
		                    
		                    if (intent.getAction().equals(Intent.ACTION_SEND)) {
		                   String message = intent.getStringExtra(Intent.EXTRA_TEXT);
		                  //Boilerpipe in AsyncTask
		                   
		                   MyResult values = null; 	  
		                   AsyncTask<String,MyResult, MyResult> asynctask=  new BoilerpipeHandler().execute(message);
		                  try {
		                      values = asynctask.get();
		                  } catch (InterruptedException e) {          
		                      e.printStackTrace();
		                  } catch (ExecutionException e) {
		                      e.printStackTrace();
		                  }
		                  Log.d("AsyncTask", "DONE!");
		                  title = values.getFirst();
		                  text = values.getSecond();
		                    	 
		                  db.addContact(new Contact(title,text));
		                  db.close();
		                  Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_LONG).show();
		                  		            
		                  		            	finish();
		                  		              }
		                  
		}
		
			}

 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
    {
 if(item.getItemId()==R.id.man_add){
	 Intent i = new Intent(this, ManAddActivity.class);
	 startActivity(i);
	 finish();
 }
		
		
        return true;
    }    
	
}
