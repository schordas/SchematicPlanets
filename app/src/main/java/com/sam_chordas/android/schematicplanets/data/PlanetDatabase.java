package com.sam_chordas.android.schematicplanets.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by sam_chordas on 8/11/15.
 */

@Database(version = PlanetDatabase.VERSION)
public final class PlanetDatabase {
    private PlanetDatabase(){}

    public static final int VERSION = 2;


        @Table(PlanetColumns.class) public static final String PLANETS = "planets";

        @Table(ArchivedPlanetColumns.class) public static final String ARCHIVED_PLANETS =
                "archived_planets";



}
