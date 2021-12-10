package com.example.outlab9.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.outlab9.MyCoreDatabase;
import com.example.outlab9.R;

public class CalendarFragment extends Fragment {

    CalendarView calendarView;
    TextView date_view;
    TextView studyplancount;
    TextView assignmentcount;
    TextView examcount;
    TextView lecturecount;
    MyCoreDatabase myCoreDatabase;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        date_view = (TextView) view.findViewById(R.id.date_view);

        studyplancount = (TextView) view.findViewById(R.id.studyplancount);

        assignmentcount = (TextView) view.findViewById(R.id.assignmentcount);

        examcount = (TextView) view.findViewById(R.id.examcount);

        lecturecount = (TextView) view.findViewById(R.id.lecturecount);

        myCoreDatabase = new MyCoreDatabase(getActivity());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date;
                if(dayOfMonth<10){
                    Date = year + "-" + (month +1) + "-0" + dayOfMonth;
                } else{
                    Date = year + "-" + (month +1) + "-" + dayOfMonth;
                }
                date_view.setText(Date);
                studyplancount.setText(Integer.toString(myCoreDatabase.getEventCount(1,Date)));
                assignmentcount.setText(Integer.toString(myCoreDatabase.getEventCount(2,Date)));
                examcount.setText(Integer.toString(myCoreDatabase.getEventCount(3,Date)));
                lecturecount.setText(Integer.toString(myCoreDatabase.getEventCount(4,Date)));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}