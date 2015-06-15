/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unicamp.br.mo409.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.navigationdrawerexample.R;
import com.unicamp.br.mo409.model.Aluno;
import com.unicamp.br.mo409.model.Professor;
import com.unicamp.br.mo409.model.Usuario;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
	public final static String TAG = "MainActivity";
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTitles;
	private Usuario user;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String tipo = this.getIntent().getStringExtra("type");
        if (tipo.equals("Aluno")){
        	//aluno
        	user = new Aluno(this.getIntent().getIntExtra("token", 0));
        }
        if (tipo.equals("Professor")){
        	//professor
        	user = new Professor(this.getIntent().getIntExtra("token", 0));
        }
        user.type = tipo;
        user.userName = this.getIntent().getStringExtra("userName");
        mTitle = mDrawerTitle = getTitle();
        //mTitle = mDrawerTitle = user.type + ": " + user.userName;
        mTitles = getMyTitles();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggleExtension(this, mDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close  /* "close drawer" description for accessibility */);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

	private String[] getMyTitles() {
		String[] myTitles = getResources().getStringArray(R.array.menu_lateral);
		
		if (user.type == "Professor"){//(exception)
			if (user.isInAula) {
				myTitles[1] = "Finalizar Aula";
			}else{
				myTitles[1] = "Iniciar Aula";
			}
		}
		return myTitles;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
            return super.onOptionsItemSelected(item);
    }

	private final class ActionBarDrawerToggleExtension extends ActionBarDrawerToggle {
		private ActionBarDrawerToggleExtension(Activity activity, DrawerLayout drawerLayout, int drawerImageRes,
				int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
		}

		@Override
		public void onDrawerClosed(View view) {
		    getActionBar().setTitle(mTitle);
		    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}

		@Override
		public void onDrawerOpened(View drawerView) {
		    getActionBar().setTitle(mDrawerTitle);
		    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}
	}
	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

	private void selectItem(int position) {
		switch (position) {
			case 0: // Disciplinas
				getDisciplinas();
				callNewFragment(position, new DisciplinasFragment());
				break;
			case 1: // check-out || check-in
				user.checkInOut();
				break;
			case 2:
				user.alterarSenha(position, this);
				break;
			case 3:
				tryLogout();
				break;
			}
    }

	private void tryLogout() {
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
		//Deslogar no servidor
       		RestClient obj = new RestClient();
    		String pathLogOut = "login/usuario/"+user.userName+"/tipo/"+user.type;
    		Log.e("Debug", "pathLogOut: "+pathLogOut);
    		String[] request = { "get", pathLogOut };
    		String retorno = null;
    		try {
    			retorno = obj.execute(request).get();
    		} catch (InterruptedException e) {
    			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
    		} catch (ExecutionException e) {
    			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
    		}
    		retorno = XmlManager.manageXmlLogout(retorno);
    		if(retorno.equals("Sucesso")){
    				finish();
			}else{
				showPopUpMessage(retorno);
			}
	}

	public void callNewFragment(int position, Fragment fragment) {
		// update the main content by replacing fragments
		Bundle args = new Bundle();
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
     //Fragment that appears in the "content_frame"

    public static class AlterarSenhaFragment extends Fragment {
        public AlterarSenhaFragment() {
            // Empty constructor required for fragment subclasses
        	//TODO
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_alterar_senha, container, false);

            getActivity().setTitle("Alterar Senha");
            return rootView;
        }
    }
    public static class DisciplinasFragment extends Fragment {
        public DisciplinasFragment() {
            // Empty constructor required for fragment subclasses
        	//TODO
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_disciplinas, container, false);

            getActivity().setTitle("Disciplinas");
            return rootView;
        }
    }
    
    private void showPopUpMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(message);
		alertDialog.show();
	}
/*    private void showToastMessage(String message){
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }*/
    
    public void getDisciplinas() {
		RestClient obj = new RestClient();
		String pathLogin = "aula/usuario/"+user.userName+"/tipo/"+user.type;
		String[] request = { "get", pathLogin };
		String retorno = null;
		try {
			retorno = obj.execute(request).get();
		} catch (InterruptedException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}
		ArrayList<String[]> ret = XmlManager.manageXmlTurmas(retorno);
		
		
		for (String[] i : ret) {
			System.out.println(i);
		}
		
		
	}
}