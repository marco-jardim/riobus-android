package com.tormentaLabs.riobus.search.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.core.utils.CoreUtils;
import com.tormentaLabs.riobus.favorite.controller.FavoriteController;
import com.tormentaLabs.riobus.search.listener.OnSearchSuggestionItemClickListener;
import com.tormentaLabs.riobus.search.view.SearchSuggestionsItemView;
import com.tormentaLabs.riobus.search.view.SearchSuggestionsItemView_;

/**
 * Created by limazix on 12/01/16.
 */
public class SearchSuggestionsCursorAdapter extends CursorAdapter {

    private LineController lineCtrl;
    private OnSearchSuggestionItemClickListener itemClickListener;

    public SearchSuggestionsCursorAdapter(Context context, Cursor c) {
        super(context, c);
        lineCtrl = new LineController();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return SearchSuggestionsItemView_.build(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String lineNumber = cursor.getString(cursor.getColumnIndex(CoreUtils.TABLE_LINES_COL_NUMBER));
        LineModel line = lineCtrl.getLine(lineNumber);

        SearchSuggestionsItemView itemView = (SearchSuggestionsItemView) view;
        itemView.bind(line, itemClickListener);
    }

    public void setItemClickListener(OnSearchSuggestionItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
