package com.example.zain.doctorrecommendersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.example.zain.doctorrecommendersystem.R.layout.row;


public class customArrayList extends ArrayAdapter<String> {

    Context context;
    String[] docName;
    String[] docSpeciality;

    public customArrayList(Context c, String[] docName, String[] docSpecialist) {
        super(c,R.layout.row,R.id.textView,docName);

        this.context = c;
        this.docName = docName;
        this.docSpeciality = docSpecialist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(row, parent, false);
        // get references.
        TextView doctorNames = (TextView) customView.findViewById(R.id.DoctorName);
        TextView doctorSpeciality = (TextView) customView.findViewById(R.id.DoctorSpeciality);
        ImageView DocImage = (ImageView) customView.findViewById(R.id.imageView);


        doctorNames.setText(docName[position]);
        doctorSpeciality.setText(docSpeciality[position]);

        DocImage.setImageResource(R.mipmap.doctorcartoon);

        return customView;
    }

}
