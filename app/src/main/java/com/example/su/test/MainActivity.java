package com.example.su.test;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
    public static Calendar APPcalendar;
    public static int maximumDaysOfMonth;
    public static int currentDate;
    public static int currentMonth;
    public static int currentYear;

    public static int yearValue;
    public static int monthValue;
    public static String monthName = "";
    public static String fisrtdayofmonth = "";
    public ArrayList<SetGetSuperCalender> ArrayCalender;

    String[] positionPre = {}, positionNext = {};

    TextView tv_month_year_header;
    CalendarAdapter1 mAdapter = null;
    ImageView img_previous_date,img_next_date;
    int day = 1;

    public  boolean firstclick = false;
    public  boolean secondclick = false;
    public  boolean samepage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,7));
        tv_month_year_header=findViewById(R.id.tv_month_year_header);


        APPcalendar=Calendar.getInstance(Locale.getDefault());
        maximumDaysOfMonth = APPcalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentDate=APPcalendar.get(Calendar.DATE);
        currentMonth=APPcalendar.get(Calendar.MONTH);
        currentYear = APPcalendar.get(Calendar.YEAR);
       Log.d("---Month",""+currentMonth);
       Log.d("---year",""+currentYear);
        Log.d("---CurrentDate",""+currentDate);
        Log.d("---DayOfMonth",""+maximumDaysOfMonth);
        //set up bar coder decoder
        ArrayCalender = new ArrayList<>();
        Calendar calendar = (Calendar) APPcalendar.clone();
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.DATE, 1);

        monthValue = calendar.get(Calendar.MONTH);
        positionPre = calendar.getTime().toString().split(" ");
        Log.d("---",""+positionPre);
        monthName = positionPre[1];
        Log.d("---",monthName);
        yearValue= Integer.parseInt(positionPre[5]);
        Log.d("---",""+yearValue);
        fisrtdayofmonth=positionPre[0];
        Log.d("---",fisrtdayofmonth);
        tv_month_year_header.setText(monthName+" "+yearValue);
        SetGetSuperCalender setGetSuperCalender=new SetGetSuperCalender();
        setGetSuperCalender.setMonth(monthValue);
        setGetSuperCalender.setYear(yearValue);
        setGetSuperCalender.setMonthBoject(dateCreation(fisrtdayofmonth));
        AddDataToMainArray(setGetSuperCalender);
        findViewById(R.id.img_next_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.img_previous_date).setVisibility(View.VISIBLE);
                Calendar next = (Calendar) APPcalendar.clone();
               Log.d("currentMonthDebo",":"+currentMonth);
                if (currentMonth > 11) {
                    currentMonth = 1;
                    currentYear++;
                    Log.d("if","if");
                } else {
                    Log.d("else","else");
                    currentMonth++;
                }

                next.set(Calendar.MONTH, currentMonth);
                next.set(Calendar.YEAR, currentYear);
                next.set(Calendar.DATE, 1);


                monthValue = next.get(Calendar.MONTH);
                Log.i("time", "" + monthValue);

                positionNext = next.getTime().toString().split(" ");
                fisrtdayofmonth = positionNext[0];
                monthName = positionNext[1];
                yearValue = Integer.parseInt(positionNext[5]);
                maximumDaysOfMonth = next.getActualMaximum(Calendar.DAY_OF_MONTH);

                Log.i("currentDate", "" + next.get(Calendar.DATE));
                Log.i("currentDay", "" + next.getActualMinimum(Calendar.DAY_OF_MONTH));
                Log.i("currentMonth", "" + next.get(Calendar.MONTH));
                Log.i("currentYear", "" + next.get(Calendar.YEAR));
                Log.i("maximumDaysOfMonth", "" + maximumDaysOfMonth);
                Log.i("fisrtDay", fisrtdayofmonth);
                Log.i("month", monthName);
                Log.i("year", "" + yearValue);

                tv_month_year_header.setText(monthName + " " + yearValue);

                for (int i = 0; i < positionNext.length; i++) {
                    Log.i("positionNext[" + i + "]", positionNext[i]);
                }
                Log.i("maximumDaysOfMonth", "" + maximumDaysOfMonth);
                //Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
                dateCreation(fisrtdayofmonth);

                SetGetSuperCalender setGetSuperCalender = new SetGetSuperCalender();
                setGetSuperCalender.setMonth(monthValue);
                setGetSuperCalender.setYear(yearValue);
                setGetSuperCalender.setMonthBoject(dateCreation(fisrtdayofmonth));
                AddDataToMainArray(setGetSuperCalender);
                if (firstclick)
                    samepage = false;

            }
        });
        findViewById(R.id.img_previous_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar pre = (Calendar) APPcalendar.clone();
                currentMonth--;

                if (currentMonth == 0) {
                    currentMonth = 12;
                    currentYear--;
                }
                pre.set(Calendar.MONTH, currentMonth);
                pre.set(Calendar.YEAR, currentYear);
                pre.set(Calendar.DATE, 1);

                monthValue = pre.get(Calendar.MONTH);
                Log.i("time", "" + monthValue);

                positionPre = pre.getTime().toString().split(" ");
                maximumDaysOfMonth = pre.getActualMaximum(Calendar.DAY_OF_MONTH);
                monthName = positionPre[1];
                yearValue = Integer.parseInt(positionPre[5]);
                fisrtdayofmonth = positionPre[0];

                Log.i("currentDate", "" + pre.get(Calendar.DATE));
                Log.i("currentDay", "" + pre.getActualMinimum(Calendar.DAY_OF_MONTH));
                Log.i("currentMonth", "" + pre.get(Calendar.MONTH));
                Log.i("currentYear", "" + pre.get(Calendar.YEAR));
                Log.i("maximumDaysOfMonth", "" + maximumDaysOfMonth);
                Log.i("fisrtDay", fisrtdayofmonth);
                Log.i("month", monthName);
                Log.i("year", "" + yearValue);

                tv_month_year_header.setText(monthName + " " + yearValue);

                Log.d("frstclick", String.valueOf(firstclick));
                Log.d("secondclick", String.valueOf(secondclick));

                dateCreation(fisrtdayofmonth);
                Boolean hasMonth = false;
                for(int i=0;i<ArrayCalender.size();i++){
                    SetGetSuperCalender prevSetGetSuperCalendar = ArrayCalender.get(i);
                    if(prevSetGetSuperCalendar.getMonth() == monthValue){
                        hasMonth = true;
                    }
                }
                if(!hasMonth){
                    SetGetSuperCalender setGetSuperCalender = new SetGetSuperCalender();
                    setGetSuperCalender.setMonth(monthValue);
                    setGetSuperCalender.setYear(yearValue);
                    setGetSuperCalender.setMonthBoject(dateCreation(fisrtdayofmonth));
                    AddDataToMainArray(setGetSuperCalender);
                }




                if (firstclick && !secondclick) {
                    for (SetGetSuperCalender sp : ArrayCalender) {
                        for (SetGetCalender data : sp.getMonthBoject()) {
                            if (data.isStartdate()) {
                                data.setStartdate(false);
                                data.setSelected(false);
                                data.setEnddate(false);
                                secondclick = false;
                                firstclick = false;
                                break;

                            }
                        }
                    }
                }


                CallView();

                for (int i = 0; i < positionPre.length; i++) {
                    Log.i("positionPre[" + i + "]", positionPre[i]);
                }
                Log.i("maximumDaysOfMonth", "" + maximumDaysOfMonth);
                Calendar tat = Calendar.getInstance();
                if (tat.get(Calendar.MONTH) == monthValue
                        && tat.get(Calendar.YEAR) == yearValue) {
                    view.findViewById(R.id.img_previous_date).setVisibility(View.GONE);
                }
            }
        });
    }

    private void AddDataToMainArray(SetGetSuperCalender setGetSuperCalender) {
        Log.d("AddDataToMainArray","AddDataToMainArray");
        Log.d("size-->>",""+ArrayCalender.size());
        for(int i=0;i<ArrayCalender.size();i++){

            SetGetSuperCalender setGetSuperCalender1 = ArrayCalender.get(i);
            Log.d("monthValueDebo",":"+setGetSuperCalender1.getMonth());
            for (SetGetCalender dd : setGetSuperCalender1.getMonthBoject()) {

                Log.d("selectedDebo",":"+dd.isSelected());
                Log.d("startedDebo",":"+dd.isStartdate());
                Log.d("endedDebo",":"+dd.isEnddate());
            }
        }
        boolean yes = true;
        for (SetGetSuperCalender data : ArrayCalender) {
            if (data.getYear() == yearValue &&
                    data.getMonth() == monthValue) {
                Log.d("debogetMonth",":"+data.getMonth());
                yes = false;
                break;
            }
        }


        if (yes) {
            Log.d("---1--","---1--");
            ArrayCalender.add(setGetSuperCalender);
        }

        CallView();
    }

    private void CallView() {
        Log.d("CallView","CallView");
        for (SetGetSuperCalender setGetSuperCalender1 : ArrayCalender) {

            Log.d("---M",""+setGetSuperCalender1.getMonth());
            Log.d("---M",""+monthValue);
            Log.d("---Y",""+ setGetSuperCalender1.getYear());
            Log.d("---y",""+yearValue);
            if (setGetSuperCalender1.getMonth() == monthValue &&
                    setGetSuperCalender1.getYear() == yearValue) {

                mAdapter = new CalendarAdapter1(setGetSuperCalender1.getMonthBoject(),MainActivity.this);
                recyclerView.setAdapter(mAdapter);
                //Loger.MSG("@@ TTTT", "YESS");
                break;
            }
        }
    }

    private ArrayList<SetGetCalender> dateCreation(String fisrtdayofmonth) {
        ArrayList<SetGetCalender> tempArray = new ArrayList<>();
        if (fisrtdayofmonth.equalsIgnoreCase("sun"))
        {
            for (int i=0;i<maximumDaysOfMonth;i++)
            {
                SetGetCalender setGetCalender=new SetGetCalender();
                setGetCalender.setDay(""+day);
                setGetCalender.setSelected(false);
                tempArray.add(setGetCalender);
                day++;
                Log.d("---day",""+day);
            }
        }
        else if (fisrtdayofmonth.equalsIgnoreCase("mon"))
        {
            for (int i = 0; i < 1; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
            }
        }
        else if (fisrtdayofmonth.equalsIgnoreCase("tue"))
        {
            for (int i = 0; i < 2; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
            }
        }
          else if (fisrtdayofmonth.equalsIgnoreCase("wed"))
        {
            for (int i = 0; i < 3; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
                Log.d("---day",""+day);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
            }
        }
        else if (fisrtdayofmonth.equalsIgnoreCase("thu"))
        {
            for (int i = 0; i < 4; i++) {

                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
            }
        }
        else if (fisrtdayofmonth.equalsIgnoreCase("fri"))
        {
            for (int i = 0; i < 5; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
            }
        }
        else if (fisrtdayofmonth.equalsIgnoreCase("sat"))
        {
            for (int i = 0; i < 6; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("");
                tempArray.add(sg);
            }
            for (int i = 0; i < maximumDaysOfMonth; i++) {
                SetGetCalender sg = new SetGetCalender();
                sg.setSelected(false);
                sg.setDay("" + day);
                tempArray.add(sg);
                day++;
                Log.d("---",""+day);
            } Log.d("---sat-day",""+day);
        }
        day=1;
        return tempArray;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap myBitmap = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(),
                    imageBitmap);
            callbarcodedata(imageBitmap);
        }*/
    }





}
