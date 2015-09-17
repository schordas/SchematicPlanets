package com.sam_chordas.android.schematicplanets.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sam_chordas on 8/11/15.
 */
public interface PlanetColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID =
            "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String NAME = "name";
    @DataType(DataType.Type.INTEGER) @NotNull public static final String DIST_FROM_SUN =
            "dist_from_sun";
    @DataType(DataType.Type.INTEGER) @NotNull public static final String IMAGE_RESOURCE =
            "image_resource";
}

