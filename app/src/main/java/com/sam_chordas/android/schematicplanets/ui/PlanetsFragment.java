package com.sam_chordas.android.schematicplanets.ui;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.schematicplanets.R;
import com.sam_chordas.android.schematicplanets.data.PlanetColumns;
import com.sam_chordas.android.schematicplanets.data.PlanetProvider;
import com.sam_chordas.android.schematicplanets.rest.Planet;
import com.sam_chordas.android.schematicplanets.rest.PlanetCursorAdapter;
import com.sam_chordas.android.schematicplanets.swipe_helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlanetsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = PlanetsFragment.class.getSimpleName();

    private static final int CURSOR_LOADER_ID = 0;
    private PlanetCursorAdapter mCursorAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    private ItemTouchHelper mItemTouchHelper;

    Planet[] planets ={
            new Planet("Mercury", 57.9, R.drawable.mercury),
            new Planet("Venus", 108.2, R.drawable.venus),
            new Planet("Earth", 149.6, R.drawable.earth),
            new Planet("Mars", 227.9, R.drawable.mars),
            new Planet("Ceres", 413.7, R.drawable.ceres),
            new Planet("Jupiter", 778.3, R.drawable.jupiter),
            new Planet("Saturn", 1427.0, R.drawable.saturn),
            new Planet("Uranus", 2871.0, R.drawable.uranus),
            new Planet("Neptune", 4497.1, R.drawable.neptune),
            new Planet("Pluto", 5913.0, R.drawable.pluto),
            new Planet("Eris", 10120.0, R.drawable.eris)
    };

    public PlanetsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Cursor c = getActivity().getContentResolver().query(PlanetProvider.Planets.CONTENT_URI,
                null, null, null, null);
        Log.i(LOG_TAG, "cursor count: " + c.getCount());
        if (c == null || c.getCount() == 0){
            insertData();
        }


        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.planet_list);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(recyclerView.getContext())
        );


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(
                R.id.fab
        );


        mCursorAdapter = new PlanetCursorAdapter(getActivity(), null);
        recyclerView.setAdapter(mCursorAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArchivedPlanetDialog archivedPlanetDialog = new ArchivedPlanetDialog();
                archivedPlanetDialog.show(getFragmentManager(), "fragment_tag");

            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCursorAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }

    public void insertData(){
        Log.d(LOG_TAG, "insert");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(planets.length);

        for (Planet planet : planets){
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    PlanetProvider.Planets.CONTENT_URI);
            builder.withValue(PlanetColumns.NAME, planet.getName());
            builder.withValue(PlanetColumns.DIST_FROM_SUN, planet.getDistFromSun());
            builder.withValue(PlanetColumns.IMAGE_RESOURCE, planet.getImageResource());
            batchOperations.add(builder.build());
        }

        try{
            getActivity().getContentResolver().applyBatch(PlanetProvider.AUTHORITY, batchOperations);
        } catch(RemoteException | OperationApplicationException e){
            Log.e(LOG_TAG, "Error applying batch insert", e);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "resume called");
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(), PlanetProvider.Planets.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        mCursorAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mCursorAdapter.swapCursor(null);
    }
}
