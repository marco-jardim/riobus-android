package com.tormentaLabs.riobus.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;
import com.tormentaLabs.riobus.search.listener.OnSearchSuggestionItemClickListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * TODO Add discription here
 *
 * @author limazix
 * @since 3.0.0
 * Created on 20/03/16
 */
@EViewGroup(R.layout.view_search_suggestions_item)
public class SearchSuggestionsItemView extends RelativeLayout implements View.OnClickListener {

    private LineModel line;
    private OnSearchSuggestionItemClickListener listener;

    @Bean
    FavoriteController favoriteController;

    @ViewById(R.id.searchSuggestionItemTitle)
    TextView title;

    @ViewById(R.id.searchSuggestionItemSubTitle)
    TextView subtitle;

    @ViewById(R.id.searchSuggestionItemIcon)
    ImageView icon;

    public SearchSuggestionsItemView(Context context) {
        super(context);
    }

    public SearchSuggestionsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchSuggestionsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(LineModel line, OnSearchSuggestionItemClickListener listener) {
        this.line = line;
        title.setText(line.number);
        subtitle.setText(line.description);

        if(favoriteController.isFavorite(line))
            icon.setImageResource(R.drawable.ic_favorite);
        else
            icon.setImageResource(R.drawable.ic_history);

        this.listener = listener;
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onSearchSuggestionItemClicked(line);
    }
}
