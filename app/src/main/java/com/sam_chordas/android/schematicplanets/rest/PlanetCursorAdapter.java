package com.sam_chordas.android.schematicplanets.rest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.schematicplanets.R;
import com.sam_chordas.android.schematicplanets.data.ArchivedPlanetColumns;
import com.sam_chordas.android.schematicplanets.data.PlanetColumns;
import com.sam_chordas.android.schematicplanets.data.PlanetProvider;
import com.sam_chordas.android.schematicplanets.swipe_helper.ItemTouchHelperAdapter;
import com.sam_chordas.android.schematicplanets.swipe_helper.ItemTouchHelperViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sam_chordas on 8/12/15.
 */
public class PlanetCursorAdapter extends CursorRecyclerViewAdapter<PlanetCursorAdapter.ViewHolder>
implements ItemTouchHelperAdapter{
    Context mContext;
    ViewHolder mVh;
    public PlanetCursorAdapter(Context context, Cursor cursor){
        super(context, cursor);
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    implements ItemTouchHelperViewHolder{
        public TextView mTextView;
        public CircleImageView mImageview;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.planet_name);
            mImageview = (CircleImageView) view.findViewById(R.id.planet_image);
        }
        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_planet, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        mVh = vh;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor){
        DatabaseUtils.dumpCursor(cursor);
        viewHolder.mTextView.setText(cursor.getString(
                cursor.getColumnIndex(PlanetColumns.NAME)));
        viewHolder.mImageview.setImageResource(cursor.getInt(cursor.getColumnIndex(
                                PlanetColumns.IMAGE_RESOURCE)));
    }


    @Override
    public void onItemDismiss(int position) {
        long cursorId = getItemId(position);
        Cursor c = getCursor();
        ContentValues cv = new ContentValues();
        cv.put(ArchivedPlanetColumns.NAME,
                c.getString(c.getColumnIndex(PlanetColumns.NAME)));
        cv.put(ArchivedPlanetColumns.DIST_FROM_SUN,
                c.getDouble(c.getColumnIndex(PlanetColumns.DIST_FROM_SUN)));
        cv.put(ArchivedPlanetColumns.IMAGE_RESOURCE,
                c.getInt(c.getColumnIndex(PlanetColumns.IMAGE_RESOURCE)));
        mContext.getContentResolver().delete(PlanetProvider.Planets.withId(cursorId),
                null, null);
        mContext.getContentResolver().insert(PlanetProvider.ArchivedPlanets.withId(cursorId),
                cv);
        notifyItemRemoved(position);
    }


}
