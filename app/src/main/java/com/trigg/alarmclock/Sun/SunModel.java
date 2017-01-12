package com.trigg.alarmclock.Sun;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 12.01.2017.
 */

public class SunModel {

    @Override
    public String toString()
    {
        return nameDay;
    }
    public long id = -1;

    public String nameDay = "test";
    public String startSun = "00:00";
    public String endSun = "00:00 ";

    public int timeHour = 0;
    public int timeMinute = 0;

    public Calendar calendar = Calendar.getInstance();
}
