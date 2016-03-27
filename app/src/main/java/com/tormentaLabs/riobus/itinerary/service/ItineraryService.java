package com.tormentaLabs.riobus.itinerary.service;

import com.tormentaLabs.riobus.itinerary.model.ItineraryModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by limazix on 05/10/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(rootUrl = RioBusUtils.SERVER_ADDR,converters = {MappingJackson2HttpMessageConverter.class})
public interface ItineraryService extends RestClientErrorHandling {

    @Get("/itinerary/{line}")
    ItineraryModel getItinarary(String line);
}
