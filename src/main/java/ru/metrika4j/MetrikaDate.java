/*
 * Copyright (C) 2012 Artur Suilin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.metrika4j;

import java.util.Calendar;
import java.util.Date;

/**
 * Представление даты в API Яндекс.Метрики. Содержит год, месяц и день, без указания времени.
 *
 * @author Artur Suilin
 */
public class MetrikaDate {
    private final int year;
    private final int month;  // 1-based
    private final int day;

    /**
     * All minutes have this many milliseconds except the last minute of the day on a day defined with
     * a leap second.
     */
    public static final long MILLISECS_PER_MINUTE = 60 * 1000;

    /** Number of milliseconds per hour, except when a leap second is inserted. */
    public static final long MILLISECS_PER_HOUR = 60 * MILLISECS_PER_MINUTE;

    /**
     * Number of leap seconds per day expect on
     * <BR/>1. days when a leap second has been inserted, e.g. 1999 JAN  1.
     * <BR/>2. Daylight-savings "spring forward" or "fall back" days.
     */
    protected static final long MILLISECS_PER_DAY = 24 * MILLISECS_PER_HOUR;


    public MetrikaDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    protected MetrikaDate(Calendar c) {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
    }


    public static MetrikaDate today() {
        return new MetrikaDate();
    }

    public static MetrikaDate yesterday() {
        return new MetrikaDate().plusDays(-1);
    }


    public MetrikaDate monthAgo() {
        Calendar c = makeCalendar();
        c.add(Calendar.MONTH, -1);
        return new MetrikaDate(c);
    }

    public MetrikaDate yearAgo() {
        Calendar c = makeCalendar();
        c.add(Calendar.YEAR, -1);
        return new MetrikaDate(c);
    }

    public MetrikaDate weekAgo() {
        Calendar c = makeCalendar();
        c.add(Calendar.WEEK_OF_MONTH, -1);
        return new MetrikaDate(c);
    }

    public MetrikaDate dayAgo() {
        Calendar c = makeCalendar();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return new MetrikaDate(c);
    }


    public MetrikaDate() {
        this(Calendar.getInstance());
    }

    /**
     * Конструирует дату из формата YYYYMMDD или YYYYMM, используемого в API
     *
     * @see #toApiString()
     */
    public MetrikaDate(String value) {
        int length = value.length();
        if (length == 6 || length == 8) {
            year = Integer.parseInt(value.substring(0, 4));
            month = Integer.parseInt(value.substring(4, 6));
            if (length == 8) {
                day = Integer.parseInt(value.substring(6, 8));
            } else {
                day = 0;
            }
        } else {
            throw new IllegalArgumentException("Invalid date format: " + value);
        }
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean hasDay() {
        return day > 0;
    }

    /**
     * Представляет дату в формате YYYYMMDD или YYYYMM, используемом в API
     *
     * @see #MetrikaDate(String)
     */
    public String toApiString() {
        return String.format("%04d%02d%02d", year, month, day);
    }

    Calendar makeCalendar() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month - 1, day);
        return c;
    }

    @Override
    public String toString() {
        return toApiString();
    }

    public Date toJavaDate() {
        return makeCalendar().getTime();
    }

    public MetrikaDate plusDays(int days) {
        Calendar c = makeCalendar();
        c.add(Calendar.DAY_OF_MONTH, days);
        return new MetrikaDate(c);
    }

    public long getUnixDay() {
        Calendar c = makeCalendar();
        long offset = c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET);
        return (long) Math.floor((double) (c.getTime().getTime() + offset) / ((double) MILLISECS_PER_DAY));
    }

    /**
     * find the number of days from this date to the given end date.
     * later end dates result in positive values.
     * Note this is not the same as subtracting day numbers.  Just after midnight subtracted from just before
     * midnight is 0 days for this method while subtracting day numbers would yields 1 day.
     *
     * @param end - any date representing the moment of time at the end of the interval for calculation.
     */
    public int diffDayPeriods(MetrikaDate end) {
        Calendar endCalendar = end.makeCalendar();
        Calendar thisCalendar = this.makeCalendar();
        long endL = endCalendar.getTimeInMillis() + endCalendar.getTimeZone().getOffset(endCalendar.getTimeInMillis());
        long startL = thisCalendar.getTimeInMillis() + thisCalendar.getTimeZone()
                .getOffset(thisCalendar.getTimeInMillis());
        long result = (endL - startL) / MILLISECS_PER_DAY;
        return (int) result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetrikaDate that = (MetrikaDate) o;
        return day == that.day && month == that.month && year == that.year;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        return result;
    }
}
