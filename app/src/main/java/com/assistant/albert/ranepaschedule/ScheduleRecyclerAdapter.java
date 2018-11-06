package com.assistant.albert.ranepaschedule;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ViewHolder> {

    private List<ScheduleItem> dataSet;
    private View scheduleCardView;

    public ScheduleRecyclerAdapter(List<ScheduleItem> dataSet) {
        this.dataSet = dataSet;
    }


    @Override
    public ScheduleRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        scheduleCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_schedule,
                parent, false);
        return new ViewHolder(scheduleCardView);
    }

    @Override
    public void onBindViewHolder(ScheduleRecyclerAdapter.ViewHolder holder, int position) {
        if (holder.scheduleOfDay.getChildCount() == 0) {
            List<CardView> subjects = new ArrayList<>();

            holder.day.setText(dataSet.get(position).getDayOfWeek());

            List<String> subjectsList = dataSet.get(position).getSubjects();
            List<String> timesList = dataSet.get(position).getTimes();

            int k = subjectsList.size() - 1;
            while (subjectsList.get(k).isEmpty() && k != 0) {
                subjectsList.remove(k);
                timesList.remove(k);
                k--;
            }

            for (int i = 0; i < subjectsList.size(); i++) {
                CardView subjectCardView = (CardView) LayoutInflater.from(holder.scheduleOfDay.getContext()).
                        inflate(R.layout.content_schedule_subject, holder.scheduleOfDay, false);
                TextView subject = subjectCardView.findViewById(R.id.subject);
                TextView subjectTime = subjectCardView.findViewById(R.id.subjectTime);
                TextView subjectNumber = subjectCardView.findViewById(R.id.subjectNumber);

                String subjectValue = dataSet.get(position).getSubjects().get(i);
                String timeValue = dataSet.get(position).getTimes().get(i);

                if (subjectValue.isEmpty()) {
                    subject.setText("-");
                } else {
                    subject.setText(subjectValue);
                }
                subjectNumber.setText(String.valueOf(i + 1));
                subjectTime.setText(timeValue);
                subjects.add(subjectCardView);
            }
            for (int i = 0; i < subjects.size(); i++) {
                holder.scheduleOfDay.addView(subjects.get(i));
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView day;
        public LinearLayout scheduleOfDay;

        public ViewHolder(View scheduleView) {
            super(scheduleView);
            day = scheduleView.findViewById(R.id.dayOfWeek);
            scheduleOfDay = scheduleView.findViewById(R.id.scheduleOfDay);
        }
    }

}
