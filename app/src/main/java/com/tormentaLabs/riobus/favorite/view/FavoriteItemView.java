package com.tormentaLabs.riobus.favorite.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteItemClickListener;
import com.tormentaLabs.riobus.favorite.listener.OnFavoriteStatusChangedListener;
import com.tormentaLabs.riobus.favorite.model.FavoriteModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by limazix on 19/11/15.
 */
@EViewGroup(R.layout.view_favorite_item)
public class FavoriteItemView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = FavoriteItemView.class.getName();
    private FavoriteModel favorite;
    private OnFavoriteItemClickListener listener;

    @ViewById(R.id.itemFavoriteStar)
    FavoriteStarView favoriteStar;

    @ViewById(R.id.favoriteLine)
    TextView favoriteLine;

    @ViewById(R.id.favoriteLineDescription)
    TextView favoriteLineDescription;

    public FavoriteItemView(Context context) {
        super(context);
    }

    public FavoriteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(FavoriteModel favorite, OnFavoriteStatusChangedListener listener) {
        this.favorite = favorite;
        favoriteLine.setText(favorite.line.number);
        favoriteLineDescription.setText(favorite.line.description);
        favoriteStar.build(favorite.line, listener);
        setOnClickListener(this);
    }

    public void setOnFavoriteItemClickListener(OnFavoriteItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onFavoriteItemClicked(favorite);
    }
}
