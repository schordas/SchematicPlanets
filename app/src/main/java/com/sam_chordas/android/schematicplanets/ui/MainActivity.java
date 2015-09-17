package com.sam_chordas.android.schematicplanets.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;
import com.sam_chordas.android.schematicplanets.R;


public class MainActivity extends ActionBarActivity {

    private static final String PLANETFRAGMENT_TAG = "PFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            Fragment f = new PlanetsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, f, PLANETFRAGMENT_TAG).commit();
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(
                                    Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(
                                    Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
