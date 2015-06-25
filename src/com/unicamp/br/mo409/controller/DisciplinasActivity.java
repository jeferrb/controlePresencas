package com.unicamp.br.mo409.controller;  
  
import java.util.ArrayList;  
import java.util.Arrays;  

import com.example.android.navigationdrawerexample.R;
  
import android.app.Activity;  
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;  
import android.view.View;
import android.widget.ArrayAdapter;  
import android.widget.ListView;  
  
public class DisciplinasActivity extends Activity {  
    
  private ListView mainListView ;  
  private ArrayAdapter<String> listAdapter ;  
    
  /** Called when the activity is first created. */  
  @Override  
  public void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_disciplinas);  
      
    // Find the ListView resource.   
    mainListView = (ListView) findViewById( R.id.mainListView );  
  
    // Create and populate a List of planet names.  
    String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};    
    ArrayList<String> planetList = new ArrayList<String>();  
    planetList.addAll( Arrays.asList(planets) );  
      
    // Create ArrayAdapter using the planet list.  
    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);  
      
    // Add more planets. If you passed a String[] instead of a List<String>   
    // into the ArrayAdapter constructor, you must not add more items.   
    // Otherwise an exception will occur.  
    listAdapter.add( "Ceres" );  
    listAdapter.add( "Pluto" );  
    listAdapter.add( "Haumea" );  
    listAdapter.add( "Makemake" );  
    listAdapter.add( "Eris" );  
      
    // Set the ArrayAdapter as the ListView's adapter.  
    mainListView.setAdapter( listAdapter );        
  }  
  
  public void tryLogout(View view){
		askForLogout();
	}
  
  private void askForLogout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Tem certeza que desaja sair?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		           @Override
				public void onClick(DialogInterface dialog, int id) {
		        	   doLogout();
		           }
		       }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
		           @Override
				public void onClick(DialogInterface dialog, int id) {
		        	   //Do nothing
		           }
		       });
		builder.show();
	}
	
	void doLogout() {
		// Deslogar no servidor
		String retorno = RestClient.doRequisition("login/usuario/Joao/tipo/Aluno");
		retorno = XmlManager.manageXmlLogout(retorno);
		if (retorno.equals("Sucesso")) {
			finish();
		} else {
			retorno = RestClient.doRequisition("login/usuario/Eliana/tipo/Professor");
			retorno = XmlManager.manageXmlLogout(retorno);
			if (retorno.equals("Sucesso")) {
				finish();
			} else {
				showPopUpMessage(retorno);
			}
		}
	}
	
	public void showPopUpMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(message);
		alertDialog.show();
	}
  
}