package com.sam_chordas.android.schematicplanets.ui;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sam_chordas.android.schematicplanets.R;
import com.sam_chordas.android.schematicplanets.data.ArchivedPlanetColumns;
import com.sam_chordas.android.schematicplanets.data.PlanetColumns;
import com.sam_chordas.android.schematicplanets.data.PlanetProvider;
import com.sam_chordas.android.schematicplanets.rest.ArchivedPlanetCursorAdapter;

/**
 * Created by sam_chordas on 8/17/15.
 */

public class ArchivedPlanetDialog extends DialogFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArchivedPlanetCursorAdapter mAdapter;

    private static final int CURSOR_LOADER_ID = 0;

    public ArchivedPlanetDialog(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.archive_dialog, container);

        ListView listView = (ListView) view.findViewById(R.id.archive_list);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_done);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArchivedPlanetDialog.this.dismiss();
            }
        });

        mAdapter = new ArchivedPlanetCursorAdapter(getActivity(), null);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                ContentValues cv = new ContentValues();
                cv.put(PlanetColumns.NAME, c.getString(c.getColumnIndex(
                        ArchivedPlanetColumns.NAME)));
                cv.put(PlanetColumns.IMAGE_RESOURCE, c.getInt(c.getColumnIndex(
                        ArchivedPlanetColumns.IMAGE_RESOURCE)));
                cv.put(PlanetColumns.DIST_FROM_SUN, c.getDouble(c.getColumnIndex(
                        ArchivedPlanetColumns.DIST_FROM_SUN
                )));
                long _id = c.getLong(c.getColumnIndex(ArchivedPlanetColumns._ID));
                getActivity().getContentResolver().delete(
                        PlanetProvider.ArchivedPlanets.withId(_id), null, null);
                getActivity().getContentResolver().insert(
                        PlanetProvider.Planets.withId(_id), cv);
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(), PlanetProvider.ArchivedPlanets.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mAdapter.swapCursor(null);
    }
}
