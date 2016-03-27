package com.tormentaLabs.riobus.history.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.tormentaLabs.riobus.history.controller.HistoryController;
import com.tormentaLabs.riobus.history.model.HistoryModel;
import com.tormentaLabs.riobus.history.view.HistoryGroupHeaderView;
import com.tormentaLabs.riobus.history.view.HistoryGroupHeaderView_;
import com.tormentaLabs.riobus.history.view.HistoryGroupItemView;
import com.tormentaLabs.riobus.history.view.HistoryGroupItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Used to expose history data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 */
@EBean
public class HistoryListAdapter extends BaseExpandableListAdapter {

    private Map<String, List<HistoryModel>> history;
    private List<String> dates;

    @RootContext
    Context context;

    @Bean
    HistoryController historyController;

    @AfterInject
    void afterInject() {
        populateLists();
    }

    public void populateLists() {
        history = historyController.getHistoryGroupedByDate();
        dates = new ArrayList<>();
        dates.addAll(history.keySet());
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return dates.size();
    }

    @Override
    public int getChildrenCount(int headerPosition) {
        return history.get(dates.get(headerPosition)).size();
    }

    @Override
    public Object getGroup(int headerPosition) {
        return dates.get(headerPosition);
    }

    @Override
    public Object getChild(int headerPosition, int childPosition) {
        return history.get(dates.get(headerPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int headerPosition) {
        return headerPosition;
    }

    @Override
    public long getChildId(int headerPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int headerPosition, boolean b, View view, ViewGroup viewGroup) {

        HistoryGroupHeaderView groupHeaderView;

        if(view == null) {
            groupHeaderView = HistoryGroupHeaderView_.build(context);
        } else {
            groupHeaderView = (HistoryGroupHeaderView) view;
        }

        groupHeaderView.bind((String) getGroup(headerPosition));
        
        return groupHeaderView;
    }

    @Override
    public View getChildView(int headerPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        
        HistoryGroupItemView groupItemView;
        
        if(view == null) {
            groupItemView = HistoryGroupItemView_.build(context);
        } else {
            groupItemView = (HistoryGroupItemView) view;
        }
        
        groupItemView.bind((HistoryModel) getChild(headerPosition, childPosition));
        
        return groupItemView;
    }

    @Override
    public boolean isChildSelectable(int headerPosition, int childPosition) {
        return true;
    }

}
