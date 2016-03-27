package com.tormentaLabs.riobus.favorite.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteItemClickListener;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteStatusChangedListener;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;
import com.tormentaLabs.riobus.favorite.view.FavoriteItemView;
import com.tormentaLabs.riobus.favorite.view.FavoriteItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

/**
 * @author limazix
 * @since 3.0
 */
@EBean
public class FavoriteAdapter extends BaseAdapter implements OnFavoriteStatusChangedListener {

    private ArrayList<FavoriteModel> favorites;

    private OnFavoriteItemClickListener listener;

    @Bean
    FavoriteController favoriteController;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        favorites = (ArrayList<FavoriteModel>) favoriteController.getAllFavorites();
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public FavoriteModel getItem(int i) {
        return favorites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FavoriteItemView favoriteItemView;

        if(view == null) {
            favoriteItemView = FavoriteItemView_.build(context);
        } else {
            favoriteItemView = (FavoriteItemView) view;
        }

        favoriteItemView.bind(getItem(i), this);

        if(listener != null)
            favoriteItemView.setOnFavoriteItemClickListener(listener);

        return favoriteItemView;
    }

    public void updateFavorites(ArrayList<FavoriteModel> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void removeFavorite(LineModel line) {
        int i = 0;
        for(FavoriteModel favorite : favorites) {
            if(favorite.line.number == line.number) {
                favorites.remove(i);
                notifyDataSetChanged();
                break;
            }
            i++;
        }
    }

    public void registerItemClickListener(OnFavoriteItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void OnFavoriteStatusChanged(LineModel line, boolean isFavorite) {
        if(!isFavorite)
            removeFavorite(line);
    }

    @Override
    public void OnFavoriteStatusChangedError(String errorMessage) {

    }
}
