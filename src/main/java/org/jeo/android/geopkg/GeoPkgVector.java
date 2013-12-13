/* Copyright 2013 The jeo project. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
