package com.tormentaLabs.riobus.search.listener;

import com.tormentaLabs.riobus.core.model.LineModel;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 20/03/16
 */
public interface OnSearchSuggestionItemClickListener {
    public void onSearchSuggestionItemClicked(LineModel line);
    public void onSearchSuggestionItemClickError(String errorMessage);
}
