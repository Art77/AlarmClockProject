package com.trigg.alarmclock;

import android.content.Context;
import android.net.Uri;

import com.trigg.alarmclock.Sun.SunModel;

import android.content.res.Resources;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmModel {

	public static final int SUNDAY = 0;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRDIAY = 5;
	public static final int SATURDAY = 6;

	public long id = -1;
	public int timeHour;
	public int timeMinute;
	private boolean repeatingDays[];
	public boolean repeatWeekly;
	public Uri alarmTone;
	public boolean isEnabled;
	
	public AlarmModel() {
		repeatingDays = new boolean[7];
	}
	
	public void setRepeatingDay(int dayOfWeek, boolean value) {
		repeatingDays[dayOfWeek] = value;
	}
	
	public boolean getRepeatingDay(int dayOfWeek) {
		return repeatingDays[dayOfWeek];
	}

    public List<SunModel> getSun()
    {
        List<SunModel> suns = new ArrayList<>();
//        try {
            if (repeatingDays.length > 0)
                for (int i = 0; i <= 6; i++) {
                    if (getRepeatingDay(i)) {
                        SunModel sun = new SunModel();
                        sun.timeMinute = this.timeMinute;
                        sun.timeHour = this.timeHour;
                        switch (i) {
                            case SUNDAY: {
                                sun.nameDay = "Вс";// Resources.getSystem().getString(R.string.reduction_sunday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                                      sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }
                            break;
                            case MONDAY: {
                                sun.nameDay = "Пн"; //Resources.getSystem().getString(R.string.reduction_monday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }
                                break;
                            case TUESDAY: {
                                sun.nameDay = "Вт";// Resources.getSystem().getString(R.string.reduction_tuesday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }   break;
                            case WEDNESDAY: {
                                sun.nameDay = "Ср"; //Resources.getSystem().getString(R.string.reduction_wednesday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }   break;
                            case THURSDAY: {
                                sun.nameDay = "Чт"; // Resources.getSystem().getString(R.string.reduction_thursday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }   break;
                            case FRDIAY: {
                                sun.nameDay = "Пт"; //Resources.getSystem().getString(R.string.reduction_friday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }   break;
                            case SATURDAY: {
                                sun.nameDay = "Сб"; // Resources.getSystem().getString(R.string.reduction_saturday);
                                while(sun.calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                                    sun.calendar.add(Calendar.DAY_OF_MONTH, 1);
                            }   break;
                        }
                        suns.add(sun);
                    }
                }
//        } catch (Exception e) { }
        return suns;
    }
}
