package com.ericsson.cds.uisdk.compositecomponents.datepicker;

import org.apache.commons.lang.StringUtils;

public enum MonthName {

    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6),
    JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

    private final int monthIndex;

    MonthName(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public boolean isAfter(MonthName anotherMonth) {
        return this.monthIndex > anotherMonth.monthIndex;
    }

    public boolean isBefore(MonthName anotherMonth) {
        return this.monthIndex < anotherMonth.monthIndex;
    }

    public static MonthName getFrom(String monthName) {
        for (MonthName month : values()) {
            if (StringUtils.equalsIgnoreCase(month.name(), monthName)) {
                return month;
            }
        }
        return null;
    }

    public static MonthName valueOf(int monthOfYear) {
        for (MonthName month : values()) {
            if (month.monthIndex == monthOfYear) {
                return month;
            }
        }
        return null;
    }

    public int getMonthNumber() {
        return monthIndex;
    }
}
