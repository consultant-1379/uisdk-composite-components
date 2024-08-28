package com.ericsson.cds.uisdk.compositecomponents.exceptions;

import com.ericsson.cifwk.meta.API;
import com.ericsson.cifwk.taf.ui.core.UiException;

import static com.ericsson.cifwk.meta.API.Quality.Deprecated;

/**
 * @deprecated please catch UiException instead
 */
@Deprecated
@API(Deprecated)
public class DatePickerException extends UiException {

    public DatePickerException(String message) {
        super(message);
    }

    public DatePickerException(Throwable exception) {
        super(exception);
    }

}
