package com.tormentaLabs.riobus.itinerary.service;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 * Class used to handle ItineraryService requests errors
 * @author limazix
 * @since 3.0
 * Created on 14/01/16.
 */
@EBean
public class ItineraryServiceErrorHandler implements RestErrorHandler {

    @RootContext
    Context context;

    @UiThread
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
