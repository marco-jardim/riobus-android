package com.tormentaLabs.riobus.marker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;
import com.tormentaLabs.riobus.marker.model.BusModel;
import com.tormentaLabs.riobus.utils.RioBusUtils;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author limazix
 * @since 3.0.0
 * Created on 26/03/16
 */
@EViewGroup(R.layout.info_window_layout)
public class BusInfoWindowView extends RelativeLayout {

    private static final String TAG = BusInfoWindowView_.class.getName();
    private BusModel bus;
    @ViewById(R.id.titulo)
    TextView busLineNumber;

    @ViewById(R.id.hora)
    TextView busDateTime;

    @ViewById(R.id.velocity)
    TextView busVelocity;

    @ViewById(R.id.sense)
    TextView busSense;

    public BusInfoWindowView(Context context) {
        super(context);
    }

    public BusInfoWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BusInfoWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(BusModel bus) {
        this.bus = bus;
        buildLineNumber();
        buildBusDateTime();
        buildVelocity();
        buildBusSense();
    }

    private void buildLineNumber() {
        String busNumber = getContext().getResources()
                .getString(R.string.marker_title, bus.getOrder(), bus.getLine());
        busLineNumber.setText(busNumber);
    }

    private void buildBusDateTime() {
        try {
            String datePrepared = prepareDate(RioBusUtils.parseStringToDateTime(bus.getTimeStamp()));
            busDateTime.setText(datePrepared);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void buildVelocity() {
        String velocity = getContext().getString(R.string.marker_velocity, String.valueOf(bus.getSpeed()));
        busVelocity.setText(velocity);
    }

    private void buildBusSense() {
        String sense;
        if(bus.getSense() == null || bus.getSense().trim().isEmpty()) {
            String nullSense = getContext().getString(R.string.marker_sense_null);
            sense = getContext().getString(R.string.marker_sense, nullSense);
        } else {
            sense = getContext().getString(R.string.marker_sense, String.valueOf(bus.getSense()));
        }
        busSense.setText(sense);
    }

    private String prepareDate(Date date){
        DateTime busTimestamp = new DateTime(date);
        DateTime now = new DateTime(Calendar.getInstance());

        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        int offset = tz.getOffset(new Date().getTime())/3600000;
        busTimestamp = busTimestamp.plusHours(offset);

        int time = Seconds.secondsBetween(busTimestamp, now).getSeconds();
        if(time < 60) return getContext().getString(R.string.marker_seconds, String.valueOf(time));

        time = Minutes.minutesBetween(busTimestamp, now).getMinutes();
        if(time < 60) return getContext().getString(R.string.marker_minutes, String.valueOf(time));

        time = Hours.hoursBetween(busTimestamp, now).getHours();
        if(time < 24) return getContext().getString(R.string.marker_hours, String.valueOf(time));

        time = Days.daysBetween(busTimestamp, now).getDays();
        return getContext().getString(R.string.marker_days, String.valueOf(time));
    }
}
