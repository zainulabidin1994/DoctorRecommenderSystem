package com.example.zain.doctorrecommendersystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyHolder> {
    ArrayList<searchHistoryJava> searchList = new ArrayList<searchHistoryJava>();
    Context context;

    public recyclerAdapter(Context c, ArrayList<searchHistoryJava> searchList) {
        this.searchList = searchList;
        this.context = c;
    }

    public recyclerAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyHolder holder = null;
        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchhistoryrow, parent, false);
            holder = new MyHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {


        final searchHistoryJava search = searchList.get(position);
        holder.symptoms.setText(search.getSymptoms());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("recyclerAdapter",search.getSymptoms()+"  "+search.getDiseases());
                Intent i = new Intent(context,ShowDisease.class);
                i.putExtra("DiseaseNames",search.getDiseases());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView symptoms;
        LinearLayout linearLayout;

        public MyHolder(View view) {


            super(view);
            symptoms = (TextView) view.findViewById(R.id.symptoms_history);
            linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        }
    }


}