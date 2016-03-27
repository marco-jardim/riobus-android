package com.tormentaLabs.riobus.favorite.listener;

import com.tormentaLabs.riobus.core.model.LineModel;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 19/03/16
 */
public interface OnFavoriteStatusChangedListener {
    public void OnFavoriteStatusChanged(LineModel line, boolean isFavorite);
    public void OnFavoriteStatusChangedError(String errorMessage);
}
