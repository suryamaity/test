package com.example.su.test;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarAdapter1 extends RecyclerView.Adapter<CalendarAdapter1.ViewHolder> {
    ArrayList<SetGetCalender> calenderArrayList;
    Context context;
    int posi=-1;
    boolean flag=true;
    public CalendarAdapter1(ArrayList<SetGetCalender> calenderArrayList, Context context) {
        this.calenderArrayList = calenderArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.form_calender_child, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarAdapter1.ViewHolder viewHolder, int i) {
        final SetGetCalender sg =calenderArrayList.get(i);
        viewHolder.day.setText(calenderArrayList.get(i).getDay());



        if (calenderArrayList.get(i).isSelected())
        {viewHolder.RR_row.setBackgroundColor(Color.YELLOW);

            sg.setSelected(true);
        }
        else {
            viewHolder.RR_row.setBackgroundColor(Color.GRAY);
            sg.setSelected(false);
        }

        viewHolder.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<calenderArrayList.size();i++)
                {
                    if(!calenderArrayList.get(i).isSelected()){
                        Log.d("---if1","if1");
                        calenderArrayList.get(i).setSelected(true);
                        viewHolder.RR_row.setBackgroundColor(Color.YELLOW);
                    }
                    else {
                        Log.d("---else1","else1");
                        calenderArrayList.get(i).setSelected(false);
                        notifyDataSetChanged();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
      //  Log.d("calenderArrayList",""+calenderArrayList.size());
        return calenderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        RelativeLayout RR_row;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RR_row=itemView.findViewById(R.id.RR_row);
            day=itemView.findViewById(R.id.day);
        }
    }
}
