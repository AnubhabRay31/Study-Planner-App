package com.example.outlab9.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlab9.AddActivity;
import com.example.outlab9.CustomAdapter;
import com.example.outlab9.MainActivity;
import com.example.outlab9.MyCoreDatabase;
import com.example.outlab9.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass..
 */
public class StudyPlanFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView no_data;
    MyCoreDatabase myCoreDatabase;
    ArrayList<String> event_id, event_title, event_course,event_type,
            event_description, event_date, event_time;
    CustomAdapter customAdapter;
    public StudyPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_plan, container, false);
        recyclerView = view.findViewById(R.id.StudyPlanRecyclerView);
        add_button = view.findViewById(R.id.addButtonStudyPlan);
        empty_imageView = view.findViewById(R.id.empty_image);
        no_data = view.findViewById(R.id.NoData_text);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        myCoreDatabase = new MyCoreDatabase(getActivity());
        event_id = new ArrayList<>();
        event_type = new ArrayList<>();
        event_title = new ArrayList<>();
        event_course = new ArrayList<>();
        event_description = new ArrayList<>();
        event_date = new ArrayList<>();
        event_time = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity(), getContext(),
                event_id, event_title, event_course, event_type, event_description, event_date, event_time);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    /**
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void storeDataInArrays(){
        int type = 1;
        Cursor cursor = myCoreDatabase.readAllData(type);
        if(cursor.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_type.add(cursor.getString(1));
                event_title.add(cursor.getString(2));
                event_course.add(cursor.getString(3));
                event_description.add(cursor.getString(4));
                event_date.add(cursor.getString(5));
                event_time.add(cursor.getString(6));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

}