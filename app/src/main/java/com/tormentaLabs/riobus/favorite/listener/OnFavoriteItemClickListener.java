package com.tormentaLabs.riobus.favorite.listener;

import com.tormentaLabs.riobus.favorite.model.FavoriteModel;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 19/03/16
 */
public interface OnFavoriteItemClickListener {
    public void onFavoriteItemClicked(FavoriteModel favorite);
    public void onFavoriteItemClickedError(String errorMessage);
}
