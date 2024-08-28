package com.ericsson.cds.uisdk.compositecomponents.tree;

import com.ericsson.cifwk.meta.API;

/**
 * UI component containing value.
 *
 * This interface is experimental. It is an attempt to make API of UI SDK components more consistent.
 *
 * @author Mihails Volkovs mihails.volkovs@ericsson.com
 *         Date: 07.07.2016
 */
@API(API.Quality.Experimental)
public interface HasValue<T> {

    T getValue();

    void setValue(T value);

}
