package com.tormentaLabs.riobus.itinerary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by limazix on 05/10/15.
 */
public class ItineraryModel {

    @JsonProperty("_id")
    private String _id;

    @JsonProperty("line")
    String line;

    @JsonProperty("description")
    String description;

    @JsonProperty("agency")
    String agency;

    @JsonProperty("keywords")
    String keywords;

    @JsonProperty("spots")
    List<SpotModel> spots;

    public ItineraryModel() {
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<SpotModel> getSpots() {
        return spots;
    }

    public void setSpots(List<SpotModel> spots) {
        this.spots = spots;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }
}
