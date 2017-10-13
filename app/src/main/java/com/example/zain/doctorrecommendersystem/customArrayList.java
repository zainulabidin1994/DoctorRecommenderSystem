package com.example.zain.doctorrecommendersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.zain.doctorrecommendersystem.R.layout.row;


    public class customArrayList extends ArrayAdapter<String> {

        Context context;
        String[] docName;
        String[] docSpeciality;
        Integer[] docImages;

        public customArrayList(Context c, String[] docName, String[] docSpecialist, Integer[] imgid) {
            super(c,R.layout.row,R.id.textView,docName);

            this.context = c;
            this.docName = docName;
            this.docSpeciality = docSpecialist;
            this.docImages = imgid;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
            View customView = myCustomInflater.inflate(row, parent, false);
            TextView doctorNames = (TextView) customView.findViewById(R.id.DoctorName);
            TextView doctorSpeciality = (TextView) customView.findViewById(R.id.DoctorSpeciality);
            final ImageView DocImage = (ImageView) customView.findViewById(R.id.doctorPicture);


            doctorNames.setText(docName[position]);
            doctorSpeciality.setText(docSpeciality[position]);

            DocImage.setImageResource(docImages[position]);
//            DocImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DocImage.setImageResource(R.drawable.doc);
//                }
//            });

            return customView;
        }

    }
