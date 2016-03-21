package com.tormentaLabs.riobus.history.controller;

import com.activeandroid.query.Select;
import com.activeandroid.query.Delete;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.history.model.HistoryModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to control history data access
 * @author limazix
 * @since 2.1
 * Created at 05/10/15
 */
@EBean
public class HistoryController {

    private static final String TAG = HistoryController.class.getName();

    @Bean
    LineController lineController;

    public List<LineModel> addLines(String[] lines) {
        ArrayList<LineModel> lineList = new ArrayList<>();
        for(String line : lines)
            lineList.add(addLine(line));

        return lineList;
    }

    public LineModel addLine(String line) {

        LineModel lineModel = lineController.createIfNotExists(line);

        HistoryModel historyItem = new HistoryModel();
        historyItem.line = lineModel;
        historyItem.createdAt = new DateTime().toString();
        historyItem.save();

        return lineModel;
    }

    public List<HistoryModel> getHistory() {
        return new Select().from(HistoryModel.class).execute();
    }

    public Map<String, List<HistoryModel>> getHistoryGroupedByDate() {
        HashMap<String, List<HistoryModel>> history = new HashMap<>();

        for(HistoryModel item: getHistory()) {
            DateTime date = new DateTime(item.createdAt);
            String key = RioBusUtils.parseDateToString(date, RioBusUtils.PATTERN_FORMAT_DATE);

            if(history.containsKey(key)) {
                history.get(key).add(item);
            } else {
                ArrayList<HistoryModel> list = new ArrayList<>();
                list.add(item);
                history.put(key, list);
            }
        }

        return history;
    }

    public void deleteHistory(){
        new Delete().from(HistoryModel.class).execute();
    }
}
