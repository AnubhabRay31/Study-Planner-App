package com.example.outlab9;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlab9.R;

import java.util.ArrayList;

public class CustomAdapterEX extends RecyclerView.Adapter<CustomAdapterEX.MyViewHolder> {

    private Context context;
    private ArrayList event_id, event_title, event_course,event_type,
            event_description, event_date, event_time;

    int position;
    Activity activity;

    public CustomAdapterEX(Activity activity,Context context,
                         ArrayList event_id,
                         ArrayList event_title,
                         ArrayList event_course,
                         ArrayList event_type,
                         ArrayList event_description,
                         ArrayList event_date,
                         ArrayList event_time ){
        this.activity = activity;
        this.context = context;
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_course = event_course;
        this.event_description = event_description;
        this.event_date = event_date;
        this.event_time = event_time;

    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CustomAdapterEX.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.event_id_txt.setText(String.valueOf(event_id.get(position)));
//        holder.event_type_txt.setText(String.valueOf(event_type.get(position)));
        holder.event_title_txt.setText(String.valueOf(event_title.get(position)));
        holder.event_course_txt.setText(String.valueOf(event_course.get(position)));
        holder.event_description_txt.setText(String.valueOf(event_description.get(position)));
        holder.event_date_txt.setText(String.valueOf(event_date.get(position)));
        holder.event_time_txt.setText(String.valueOf(event_time.get(position)));

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateActivityEX.class);
                intent.putExtra("id",String.valueOf(event_id.get(position)));
//                intent.putExtra("type",String.valueOf(event_type.get(position)));//datatype error
                intent.putExtra("title",String.valueOf(event_title.get(position)));
                intent.putExtra("course",String.valueOf(event_course.get(position)));
                intent.putExtra("description",String.valueOf(event_description.get(position)));
                intent.putExtra("date",String.valueOf(event_date.get(position)));
                intent.putExtra("time",String.valueOf(event_time.get(position)));
//                context.startActivity(intent);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return event_id.size();//can use any of the arrays for size finding
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_id_txt, event_title_txt, event_course_txt,event_type_txt,
                event_description_txt, event_date_txt, event_time_txt;

        //newly-added-----------------1 line----------------
        LinearLayout rowLayout;

        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            event_id_txt = itemView.findViewById(R.id.event_id);
            event_title_txt = itemView.findViewById(R.id.event_title);
            event_course_txt = itemView.findViewById(R.id.event_course);
//            event_type_txt = itemView.findViewById(R.id.event_type);
            event_description_txt = itemView.findViewById(R.id.event_description);
            event_date_txt = itemView.findViewById(R.id.event_date);
            event_time_txt = itemView.findViewById(R.id.event_time);

            //newly-added-------------1 line---------------------
            rowLayout = itemView.findViewById(R.id.row_Layout);
        }
    }
}
