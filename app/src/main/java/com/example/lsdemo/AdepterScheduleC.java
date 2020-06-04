package com.example.lsdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AdepterScheduleC extends BaseAdapter {


    public List<Classcricketschedule> scheduleList;

    public AdepterScheduleC(List<Classcricketschedule>scheduleList){

        this.scheduleList=scheduleList;

    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_view_layout,null);

        TextView team1=view.findViewById(R.id.textTeamA);
        TextView team2=view.findViewById(R.id.textTeamB);
        TextView time=view.findViewById(R.id.textTime);
        TextView date=view.findViewById(R.id.textDate);
        TextView location=view.findViewById(R.id.textLocation);

        team1.setText(scheduleList.get(position).getTeam1());
        team2.setText(scheduleList.get(position).getTeam2());
        time.setText("Time: "+scheduleList.get(position).getTime());
        date.setText("Date: "+scheduleList.get(position).getDate());
        location.setText("Location: "+scheduleList.get(position).getLocation());


        return (view);
    }


}
