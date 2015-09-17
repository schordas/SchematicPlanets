package com.sam_chordas.android.schematicplanets.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by sam_chordas on 8/11/15.
 */

@ContentProvider(authority = PlanetProvider.AUTHORITY, database = PlanetDatabase.class)
public final class PlanetProvider {
    public static final String AUTHORITY =
            "com.sam_chordas.android.schematicplanets.data.PlanetProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String PLANETS = "planets";
        String ARCHIVED_PLANETS="archived_planets";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = PlanetDatabase.PLANETS) public static class Planets{
        @ContentUri(
                path = Path.PLANETS,
                type = "vnd.android.cursor.dir/planet",
                defaultSort = PlanetColumns.DIST_FROM_SUN + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.PLANETS);

        @InexactContentUri(
                name = "PLANET_ID",
                path = Path.PLANETS + "/#",
                type = "vnd.android.cursor.item/planet",
                whereColumn = PlanetColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.PLANETS, String.valueOf(id));
        }

//        @NotifyInsert(paths = Path.PLANETS) public static Uri [] onInsert(ContentValues values){
//            final long id = values.getAsLong(PlanetColumns._ID);
//            return new Uri [] {withId(id)};
//        }

//        @NotifyDelete(paths = Path.PLANETS + "/#") public static Uri onDelete(Context context,
//                                                                              Uri uri) {
//            final long noteId = Long.valueOf(uri.getPathSegments().get(1));
//            Cursor c = context.getContentResolver().query(uri, null, null, null, null);
//            c.moveToFirst();
//            final long listId = c.getLong(c.getColumnIndex(NoteColumns.LIST_ID));
//            c.close();
//
//            return new Uri[] {
//                    withId(noteId), fromList(listId), Lists.withId(listId),
//            };
    }

    @TableEndpoint(table = PlanetDatabase.ARCHIVED_PLANETS) public static class ArchivedPlanets{
        @ContentUri(
                path = Path.ARCHIVED_PLANETS,
                type = "vnd.android.cursor.dir/archived_planet",
                defaultSort = ArchivedPlanetColumns.DIST_FROM_SUN + " ASC"
        )
        public static final Uri CONTENT_URI = buildUri(Path.ARCHIVED_PLANETS);

        @InexactContentUri(
                name = "ARCHIVED_PLANET_ID",
                path = Path.ARCHIVED_PLANETS + "/#",
                type = "vnd.android.cursor.item/archived_planet",
                whereColumn = ArchivedPlanetColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(long id){
            return buildUri(Path.ARCHIVED_PLANETS, String.valueOf(id));
        }
    }
}
