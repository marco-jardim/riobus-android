package com.tormentaLabs.riobus.marker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tormentaLabs.riobus.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author limazix
 * @since 3.0.0
 * Created on 26/03/16
 */
@EViewGroup(R.layout.view_info_window_user)
public class UserInfoWindowView extends RelativeLayout {

    @ViewById(R.id.titulo)
    TextView title;

    public UserInfoWindowView(Context context) {
        super(context);
    }

    public UserInfoWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserInfoWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind() {
        title.setText(R.string.marker_user);
    }
}
