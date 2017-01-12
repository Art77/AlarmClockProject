package com.trigg.alarmclock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.trigg.alarmclock.Sun.SunListAdapter;
import com.trigg.alarmclock.Sun.SunModel;

public class AlarmListAdapter extends BaseAdapter {

	private Context mContext;
	private List<AlarmModel> mAlarms;
    private Double latitude = 53.220000;
    private Double longitude = 45.020000;

    private static final String TAG = "AlarmListAdapter";

    public AlarmListAdapter(Context context, List<AlarmModel> alarms) {
		mContext = context;
		mAlarms = alarms;
	}
	
	public void setAlarms(List<AlarmModel> alarms) {
		mAlarms = alarms;
	}
	
	@Override
	public int getCount() {
		if (mAlarms != null) {
			return mAlarms.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mAlarms != null) {
			return mAlarms.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (mAlarms != null) {
			return mAlarms.get(position).id;
		}
		return 0;
	}


	@Override
	public View getView(int position, View view, ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.alarm_list_item, parent, false);
		}
		
		AlarmModel model = (AlarmModel) getItem(position);
        this.alwaysSun(model, mContext, view);

		TextView txtTime = (TextView) view.findViewById(R.id.alarm_item_time);
		txtTime.setText(String.format("%02d : %02d", model.timeHour, model.timeMinute));

		updateTextColor((TextView) view.findViewById(R.id.alarm_item_monday), model.getRepeatingDay(AlarmModel.MONDAY));
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_tuesday), model.getRepeatingDay(AlarmModel.TUESDAY));
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_wednesday), model.getRepeatingDay(AlarmModel.WEDNESDAY));
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_thursday), model.getRepeatingDay(AlarmModel.THURSDAY));
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_friday), model.getRepeatingDay(AlarmModel.FRDIAY));		
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_saturday), model.getRepeatingDay(AlarmModel.SATURDAY));
		updateTextColor((TextView) view.findViewById(R.id.alarm_item_sunday), model.getRepeatingDay(AlarmModel.SUNDAY));

		ToggleButton btnToggle = (ToggleButton) view.findViewById(R.id.alarm_item_toggle);
		btnToggle.setChecked(model.isEnabled);
		btnToggle.setTag(Long.valueOf(model.id));
		btnToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((AlarmListActivity) mContext).setAlarmEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
			}
		});


        Button btnDelete = (Button) view.findViewById(R.id.alarm_item_delete);
        btnDelete.setTag(Long.valueOf(model.id));
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AlarmListActivity) mContext).ondeleAlarm(((Long) view.getTag()).longValue());
            }
        });

		view.setTag(Long.valueOf(model.id));
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				((AlarmListActivity) mContext).startAlarmDetailsActivity(((Long) view.getTag()).longValue());
			}
		});
		
		view.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				((AlarmListActivity) mContext).deleteAlarm(((Long) view.getTag()).longValue());
				return true;
			}
		});
		
		return view;
	}
	
	private void updateTextColor(TextView view, boolean isOn) {
		if (isOn) {
			view.setTextColor(Color.GREEN);
		} else {
			view.setTextColor(Color.BLACK);
		}
	}

    private void alwaysSun(AlarmModel alarm, Context context, View view)
    {
        List<SunModel> sunModelList = alarm.getSun();
        List<SunModel> listGeo = new ArrayList<>();

        for (int i = 0; i < sunModelList.size(); i++) {

            com.luckycatlabs.sunrisesunset.dto.Location location =
                      //  new com.luckycatlabs.sunrisesunset.dto.Location("39.9522222", "-75.1641667");
                    new com.luckycatlabs.sunrisesunset.dto.Location(latitude, longitude);
            Log.v(TAG, "latitude=" + latitude);
            Log.v(TAG, "longitude=" + longitude);
            SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Russia");

            Calendar sunrise = calculator.getOfficialSunriseCalendarForDate(sunModelList.get(i).calendar);
            Calendar sunset = calculator.getOfficialSunsetCalendarForDate(sunModelList.get(i).calendar);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sunModelList.get(i).startSun = sdf.format(sunrise.getTime());
            sunModelList.get(i).endSun = sdf.format(sunset.getTime());
            listGeo.add(sunModelList.get(i));
        }
        SunListAdapter adapter = new SunListAdapter(context, R.layout.sun_list_row, listGeo);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }

    public void setLatitude(double latitude)  {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)  {
        this.longitude = longitude;
    }
}
