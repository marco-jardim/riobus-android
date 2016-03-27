package com.tormentaLabs.riobus.marker.service;

import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * Created by limazix on 02/09/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(rootUrl = RioBusUtils.SERVER_ADDR, converters = {MappingJackson2HttpMessageConverter.class})
public interface BusService extends RestClientErrorHandling {

    @Get("/search/{line}")
    List<BusModel> getBusesByLine(String line);

}
