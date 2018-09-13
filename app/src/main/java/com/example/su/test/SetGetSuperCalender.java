package com.example.su.test;

import java.util.ArrayList;

public class SetGetSuperCalender {
    int Month;
    int Year;
    ArrayList<SetGetCalender> MonthBoject;

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public ArrayList<SetGetCalender> getMonthBoject() {
        return MonthBoject;
    }

    public void setMonthBoject(ArrayList<SetGetCalender> monthBoject) {
        MonthBoject = monthBoject;
    }
}
