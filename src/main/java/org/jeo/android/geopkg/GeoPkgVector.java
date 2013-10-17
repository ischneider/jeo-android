package org.jeo.android.geopkg;

import java.io.IOException;

import org.jeo.data.Cursor;
import org.jeo.data.Query;
import org.jeo.data.VectorDataset;
import org.jeo.feature.Feature;
import org.jeo.feature.Schema;

public class GeoPkgVector extends GeoPkgDataset<FeatureEntry> implements VectorDataset {

    public GeoPkgVector(FeatureEntry entry, GeoPkgWorkspace geopkg) {
        super(entry, geopkg);
    }

    @Override
    public Schema schema() throws IOException {
        return geopkg.schema(entry);
    }

    @Override
    public long count(Query q) throws IOException {
        return geopkg.count(entry, q);
    }
    
    @Override
    public Cursor<Feature> cursor(Query q) throws IOException {
        return geopkg.cursor(entry, q);
    }

}
