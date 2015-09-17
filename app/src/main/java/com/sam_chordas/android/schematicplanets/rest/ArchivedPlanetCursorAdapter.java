package com.sam_chordas.android.schematicplanets.rest;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sam_chordas.android.schematicplanets.R;
import com.sam_chordas.android.schematicplanets.data.ArchivedPlanetColumns;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by sam_chordas on 8/18/15.
 * Credit to skyfishjy gist:
 *    https://gist.github.com/skyfishjy/443b7448f59be978bc59
 * for the code structure  
 */
public class ArchivedPlanetCursorAdapter extends CursorAdapter{
    private static final String LOG_TAG = ArchivedPlanetCursorAdapter.class.getSimpleName();

    public static class ViewHolder{
        public final CircleImageView imageView;
        public final TextView textView;
        public ViewHolder(View view){
            imageView = (CircleImageView) view.findViewById(R.id.archive_planet_image);
            textView = (TextView) view.findViewById(R.id.archive_planet_name);
        }
    }

    public ArchivedPlanetCursorAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_archive_planet,
                parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(
                ArchivedPlanetColumns.NAME
        )));
        viewHolder.imageView.setImageResource(cursor.getInt(cursor.getColumnIndex(
                ArchivedPlanetColumns.IMAGE_RESOURCE
        )));
    }
}
