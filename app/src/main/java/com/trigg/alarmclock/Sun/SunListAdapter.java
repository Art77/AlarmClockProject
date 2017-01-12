package com.trigg.alarmclock.Sun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trigg.alarmclock.R;

import java.util.List;

/**
 * Created by user on 12.01.2017.
 */

public class SunListAdapter extends ArrayAdapter {

    private List<SunModel> mSuns;
    private Context mContext;

    public  SunListAdapter(Context context, int resource, List<SunModel> models) {
        super(context, resource);
        this.mContext = context;
        this.mSuns = models;
    }

    @Override
    public Object getItem(int position) {
        if (mSuns != null) {
            return mSuns.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mSuns != null) {
            return mSuns.size();
        }
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parant) {
        return initView(position, view, parant);
    }

    private View initView(int position, View view, ViewGroup parent)
    {
        if(view == null)   {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate(R.layout.sun_list_row, parent, false);
        }

        SunModel sun = (SunModel) getItem(position);

        TextView day = (TextView) view.findViewById(R.id.day_alarm);
        day.setText(sun.nameDay);

        TextView starSun = (TextView) view.findViewById(R.id.start_sun);
        starSun.setText(sun.startSun);

        TextView endSun = (TextView) view.findViewById(R.id.end_sun);
        endSun.setText(sun.endSun);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return initView(position, convertView, parent);
    }

}
