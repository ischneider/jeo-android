package org.jeo.android.mbtiles;

import java.io.IOException;

import org.jeo.data.Cursor;
import org.jeo.data.Tile;

public class TileCursor extends Cursor<Tile> {

    android.database.Cursor cursor;
    MBTileSet tileset;

    Boolean next = null;
    
    TileCursor(android.database.Cursor cursor, MBTileSet tileset) {
        this.cursor = cursor;
        this.tileset = tileset;
    }
    
    @Override
    public boolean hasNext() throws IOException {
        if (next == null) {
            next = cursor.moveToNext();
        }
    
        return next;
    }

    @Override
    public Tile next() throws IOException {
        try {
            if (next != null && next.booleanValue()) {
                Tile t = new Tile();
                t.setZ(cursor.getInt(0));
                t.setX(cursor.getInt(1));
                t.setY(cursor.getInt(2));
                t.setData(cursor.getBlob(3));
                t.setMimeType(tileset.getTileFormat());

                return t;
            }
            return null;
        }
        finally {
            next = null;
        }
    }

    @Override
    public void close() throws IOException {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

}
