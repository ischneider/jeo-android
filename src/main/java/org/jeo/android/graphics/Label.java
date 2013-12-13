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
package org.jeo.android.graphics;

import java.util.HashMap;
import java.util.Map;

import org.jeo.feature.Feature;
import org.jeo.geom.Envelopes;
import org.jeo.map.Rule;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Abstract class for labels.
 * 
 * @author Justin Deoliveira, OpenGeo
 */
public abstract class Label {

    String text;
    Rule rule;
    Feature feature;
    Geometry geom;

    Geometry shape = null;
    Map<Object,Object> stuff; 

    public Label(String text, Rule rule, Feature feature, Geometry geom) {
        this.text = text;
        this.rule = rule;
        this.feature = feature;
        this.geom = geom;
    }

    /**
     * The label text.
     */
    public String getText() {
        return text;
    }

    /**
     * The rule the label originated from.
     * <p>
     * The rule contains all the properties that control the layout and rendering of the label. 
     * </p>
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * The feature the label originated from.
     */
    public Feature getFeature() {
        return feature;
    }

    /**
     * The rendered feature geometry.
     * <p>
     * This may not be the same as <tt>getFeature().geometry()</tt> due to clipping/etc..  
     * </p>
     */
    public Geometry getGeometry() {
        return geom;
    }

    /**
     * Attaches renderer specific information to the label object.
     * 
     * @param key The key of the object.
     * @param value The value of the object.
     */
    public void put(Object key, Object value) {
        if (stuff == null) {
            stuff = new HashMap<Object, Object>();
        }
        stuff.put(key, value);
    }

    /**
     * Retrieves renderer specific information from the label object.
     * 
     * @param key The key of the value.
     * @param clazz The expected type of the value.
     *  
     * @return The value or <code>null</code> if it doesn't exist. 
     */
    public <T> T get(Object key, Class<T> clazz) {
        return clazz.cast(stuff != null ? stuff.get(key) : null);
    }

    /**
     * Returns the priority of the label obtained from the underlying rule and feature, looking 
     * up the rule property "text-priority". 
     */
    public Comparable priority() {
        return rule.eval(feature, "text-priority", Comparable.class, 1f);
    }

    /**
     * The bounds of the label.
     * <p>
     * This bounds is used as the key for the label in the underlying spatial index.
     * </p>
     */
    public abstract Envelope bounds();

    /**
     * Sets the detailed shape of the label.
     * <p>
     * This value is used when determining if two labels actually overlap even if the bounds 
     * overlap.  
     * </p>
     */
    public void setShape(Geometry shape) {
        this.shape = shape;
    }

    /**
     * Derived shape of the label.
     * <p>
     * This method returns the shape set by {@link #setShape(Geometry)} if it has been called. If 
     * not it returns {@link #bounds()} as a Polygon.
     * </p>
     */
    public Geometry shape() {
        if (shape != null) {
            return shape;
        }

        Envelope bounds = bounds();
        if (bounds == null) {
            throw new IllegalStateException("Label has no bounds or shape");
        }

        return Envelopes.toPolygon(bounds);
    }

    @Override
    public String toString() {
        return text;
    }
}
